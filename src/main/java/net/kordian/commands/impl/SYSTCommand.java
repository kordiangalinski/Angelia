package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * The type Syst command.
 */
public class SYSTCommand extends Command {

    /**
     * Instantiates a new Syst command.
     */
    public SYSTCommand() {
        super("SYST");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient("215 the FTP Server");

        return state;
    }
}
