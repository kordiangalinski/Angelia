package net.kordian.commands.impl;

import net.kordian.authorization.UserStatus;
import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * The type User command.
 */
public class USERCommand extends Command {
    /**
     * Instantiates a new User command.
     */
    public USERCommand() {
        super("USER");
    }

    @Override
    public ConnectionState handler(String username,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        if (state.getUserStatus() == UserStatus.LOGGED_IN) {
            controlConnection.sendToClient("530 User already logged in");
            return state;
        }
        state.setUser(username);
        state.setUserStatus(UserStatus.ENTERED_USERNAME);
        controlConnection.sendToClient("331 User name okay, need password");
        return state;
    }
}