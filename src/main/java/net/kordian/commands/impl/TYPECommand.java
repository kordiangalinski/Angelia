package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferType;

/**
 * The type TYPE command.
 */
public class TYPECommand extends Command {

    /**
     * Instantiates a new Type command.
     */
    public TYPECommand() {
        super("TYPE");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        switch (args.toUpperCase()) {
            case "A" -> {
                dataConnection.setDataTransferType(DataTransferType.ASCII);
                controlConnection.sendToClient("200 OK");
            }
            case "I" -> {
                dataConnection.setDataTransferType(DataTransferType.BINARY);
                controlConnection.sendToClient("200 OK");
            }
            default -> {
                controlConnection.sendToClient("504 Not OK");
            }
        }

        return state;
    }
}
