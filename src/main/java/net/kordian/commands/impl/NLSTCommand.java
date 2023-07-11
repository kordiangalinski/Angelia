package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.exceptions.CannotCloseDataConnection;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Nlst command.
 */
public class NLSTCommand extends Command {

    /**
     * Instantiates a new Nlst command.
     */
    public NLSTCommand() {
        super("NLST");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) throws CannotCloseDataConnection {

        if (dataConnection.getDataConnection() == null || dataConnection.getDataConnection().isClosed()) {
            controlConnection.sendToClient("425 No data connection was established");
            return state;
        }

        List<String> dirContent = handleList(args, state.getCurrDir());
        if (dirContent == null) {
            controlConnection.sendToClient("550 File does not exist.");
            return state;
        }

        controlConnection.sendToClient("125 Opening ASCII mode data connection for file list.");
        for(String s : dirContent) {
            dataConnection.sendToClient(s);
        }

        controlConnection.sendToClient("226 Transfer complete.");
        dataConnection.closeConnection();

        return state;
    }

    private List<String> handleList(String args, String dir) {
        String directory = getDirectoryPath(args, dir);

        File file = new File(directory);
        if (!file.exists()) {
            return null;
        }

        return getListFormat(file);
    }

    private String getDirectoryPath(String args, String dir) {
        String filename = dir;
        if (args != null) {
            filename = filename + File.separator + args;
        }
        return filename;
    }

    private List<String> getListFormat(File directory) {
        List<String> directoryContent = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                directoryContent.add(file.getName());
            }
        }

        return directoryContent;
    }
}
