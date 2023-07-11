package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferMode;

/**
 * The type Pwd command.
 */
public class PWDCommand extends Command {

    /**
     * Instantiates a new Pwd command.
     */
    public PWDCommand() {
        super("PWD");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient(String.format("257 \"%s\"", state.getCurrDir()));

        return state;
    }
}
