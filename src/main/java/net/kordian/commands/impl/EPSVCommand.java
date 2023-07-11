package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferMode;

/**
 * The type Epsv command.
 */
public class EPSVCommand extends Command {

    /**
     * Instantiates a new Epsv command.
     */
    public EPSVCommand() {
        super("EPSV");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient("229 Entering Extended Passive Mode (|||" + dataConnection.getDataPort() + "|)");
        dataConnection.openConnection(DataTransferMode.PASSIVE, dataConnection.getDataPort());
        return state;
    }
}
