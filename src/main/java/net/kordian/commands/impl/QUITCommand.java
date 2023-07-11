package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * The type Quit command.
 */
public class QUITCommand extends Command {

    /**
     * Instantiates a new Quit command.
     */
    public QUITCommand() {
        super("QUIT");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient("221 Closing connection");
        state.setEstablished(false);

        return state;
    }
}
