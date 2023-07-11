package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Mkd command.
 */
public class MKDCommand extends Command {

    /**
     * Instantiates a new Mkd command.
     */
    public MKDCommand() {
        super("MKD");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        if (args != null) {
            String newDirectoryPath = state.getCurrDir() + File.separator + args;
            File newDirectory = new File(newDirectoryPath);

            if (newDirectory.mkdir()) {
                controlConnection.sendToClient("250 Directory successfully created");
            } else {
                controlConnection.sendToClient("550 Failed to create new directory");
            }
        } else {
            controlConnection.sendToClient("550 Invalid name");
        }

        return state;
    }
}
