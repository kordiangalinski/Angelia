package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.exceptions.CannotCloseDataConnection;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Retr command.
 */
// TODO: Some issue with encoding
public class RETRCommand extends Command {

    /**
     * Instantiates a new Retr command.
     */
    public RETRCommand() {
        super("RETR");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) throws CannotCloseDataConnection {
        File file = new File(state.getCurrDir() + File.separator + args);

        if(!file.exists()) {
            controlConnection.sendToClient("550 File doesn't exist");
            return state;
        }

        controlConnection.sendToClient("150 Opening binary mode data connection for requested file " + file.getName());

        dataConnection.sendFile(file, dataConnection.getDataTransferType());
        controlConnection.sendToClient("226 File transfer successful. Closing data connection.");
        dataConnection.closeConnection();

        return state;
    }
}
