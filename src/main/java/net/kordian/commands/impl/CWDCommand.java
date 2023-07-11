package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Cwd command.
 */
public class CWDCommand extends Command {

    /**
     * Instantiates a new Cwd command.
     */
    public CWDCommand() {
        super("CWD");
    }

    @Override
    public ConnectionState handler(String arg, ConnectionState state, ControlConnection controlConnection, DataConnection dataConnection) {
        String currentDirectory = state.getCurrDir();

        if (arg.equals("..")) {
            int index = currentDirectory.lastIndexOf(File.separator);
            if (index > 0) {
                currentDirectory = currentDirectory.substring(0, index);
            }
        } else if (!arg.equals(".")) {
            currentDirectory = currentDirectory + File.separator + arg;
        }

        File file = new File(currentDirectory);

        if (isValidDirectory(file, state.getRootDir())) {
            state.setCurrDir(currentDirectory);
            controlConnection.sendToClient("250 The current directory has been changed to " + currentDirectory);
        } else {
            controlConnection.sendToClient("550 Requested action not taken. File unavailable.");
        }

        return state;
    }

    private boolean isValidDirectory(File file, String rootDir) {
        return file.exists() && file.isDirectory() && file.getPath().startsWith(rootDir);
    }

}
