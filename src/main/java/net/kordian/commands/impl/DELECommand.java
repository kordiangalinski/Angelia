package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

import java.io.File;

/**
 * The type Dele command.
 */
public class DELECommand extends Command {

    /**
     * Instantiates a new Dele command.
     */
    public DELECommand() {
        super("DELE");
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        controlConnection.sendToClient(String.format("257 \"%s\"", state.getCurrDir()));
        File fileToDelete = new File(state.getCurrDir() + File.separator + args);

        if (!fileToDelete.exists()) {
            controlConnection.sendToClient("550 File does not exist");
        } else {
            if (fileToDelete.delete()) {
                controlConnection.sendToClient("250 File deleted");
            } else {
                controlConnection.sendToClient("450 File deletion error");
            }
        }
        return state;
    }
}
