package net.kordian.connections;

import lombok.AllArgsConstructor;
import net.kordian.authorization.UserStatus;
import net.kordian.commands.Command;
import net.kordian.commands.CommandFactory;
import net.kordian.exceptions.CannotCloseDataConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * The type Connection handler.
 */
@AllArgsConstructor
public class ConnectionHandler extends Thread {
    private final Map<String, Command> commands;
    private ConnectionState connectionState = new ConnectionState();

    private ControlConnection controlConnection;
    private DataConnection dataConnection = new DataConnection();


    /**
     * Instantiates a new Connection handler.
     *
     * @param con the con
     * @throws IOException the io exception
     */
    public ConnectionHandler(Socket con) throws IOException {
        super();
        this.controlConnection = new ControlConnection(con);
        this.commands = CommandFactory.createCommands();
    }

    private String extractCommand(String commandLine) {
        String[] parts = commandLine.split(" ", 2);
        return parts[0].toUpperCase();
    }

    private String extractArguments(String commandLine) {
        String[] parts = commandLine.split(" ", 2);
        return parts.length > 1 ? parts[1] : null;
    }

    private void handleCommand(String command) throws CannotCloseDataConnection {
        if (command == null) {
            this.controlConnection.sendToClient("500 Syntax error, command unrecognized");
            return;
        }

        String args = extractArguments(command);
        command = extractCommand(command);

         Command cmd = commands.get(command);
         if (cmd != null) {
             System.out.println(command);
             if (this.connectionState.getUserStatus() == UserStatus.LOGGED_IN) {
                this.connectionState = cmd.handler(args, connectionState, controlConnection, dataConnection);
             } else if (command.equals("USER") || command.equals("PASS")) {
                 this.connectionState = cmd.handler(args, connectionState, controlConnection, dataConnection);
             } else {
                 this.controlConnection.sendToClient("530 Not logged in");
             }
        } else {
             this.controlConnection.sendToClient("501 Unknown command");
        }
    }

    private void close() {
        try {
            this.controlConnection.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        controlConnection.sendToClient("220 Welcome to FTP Server");
        try {
            String command;
            while (this.connectionState.isEstablished() && (command = controlConnection.getClientInput()) != null) {
                handleCommand(command);
            }
        } catch (IOException | CannotCloseDataConnection e) {
            // TODO: Error handling
        } finally {
            close();
        }
    }

}
