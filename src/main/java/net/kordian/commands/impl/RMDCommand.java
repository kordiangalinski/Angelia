package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Rmd command.
 */
public class RMDCommand extends Command {

    /**
     * Instantiates a new Rmd command.
     */
    public RMDCommand() {
        super("RMD");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        String dirName = state.getCurrDir();

        if (args != null) {
            dirName = dirName + File.separator + args;

            File dir = new File(dirName);

            if (dir.exists() && dir.isDirectory()) {
                if (dir.delete()) {
                    controlConnection.sendToClient("250 Directory was successfully removed");
                } else {
                    controlConnection.sendToClient("550 Failed to remove the directory");
                }
            } else {
                controlConnection.sendToClient("550 Requested action not taken. File unavailable.");
            }
        } else {
            controlConnection.sendToClient("550 Invalid file name.");
        }

        return state;
    }

}
