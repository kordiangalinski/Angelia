package net.kordian.commands.impl;

import net.kordian.authorization.UserStatus;
import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * The type Pass command.
 */
public class PASSCommand extends Command {
    /**
     * Instantiates a new Pass command.
     */
    public PASSCommand() {
        super("PASS");
    }


    @Override
    public ConnectionState handler(String pass,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        if (state.getUserStatus() == UserStatus.LOGGED_IN) {
            controlConnection.sendToClient("530 User already logged in");
            return state;
        }

        if (state.getUserStatus() == UserStatus.ENTERED_USERNAME && state.getAuthManager().isAuthorized(state.getUser(), pass)) {
            state.setUserStatus(UserStatus.LOGGED_IN);
            controlConnection.sendToClient("230 User logged on, proceed");
        } else {
            controlConnection.sendToClient("530 Not logged in");
        }

        return state;
    }
}
