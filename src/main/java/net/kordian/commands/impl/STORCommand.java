package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.exceptions.CannotCloseDataConnection;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Stor command.
 */
public class STORCommand extends Command {

    /**
     * Instantiates a new Stor command.
     */
    public STORCommand() {
        super("PWD");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) throws CannotCloseDataConnection {
        if (args == null) {
            controlConnection.sendToClient("501 No filename given");
            dataConnection.closeConnection();
            return state;
        }

        File file = new File(state.getCurrDir() + File.separator + args);
        if (file.exists()) {
            controlConnection.sendToClient("550 File already exists");
        } else {
            switch (dataConnection.getDataTransferType()) {
                case ASCII -> controlConnection.sendToClient("150 Opening ASCII mode data connection for requested file " + file.getName());
                case BINARY -> controlConnection.sendToClient("150 Opening binary mode data connection for requested file " + file.getName());
            }
            dataConnection.storeFile(file, dataConnection.getDataTransferType());
            controlConnection.sendToClient("226 File transfer successful. Closing data connection.");
        }
        dataConnection.closeConnection();

        return state;
    }
}
