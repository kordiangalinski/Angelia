package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * The type Feat command.
 */
public class FEATCommand extends Command {

    /**
     * Instantiates a new Feat command.
     */
    public FEATCommand() {
        super("FEAT");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient("211-Extensions supported:");
        // TODO: Extensions: AUTH_GSSAPI, SIZE, MDTM, UTF8, REST_STREAM, AUTH_TLS
        controlConnection.sendToClient("211 END");

        return state;
    }
}
