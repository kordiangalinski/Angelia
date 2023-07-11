/**
 * Package containing server commands.
 */
package net.kordian.commands;

import lombok.AllArgsConstructor;
import net.kordian.connections.CannotCloseDataConnection;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;

/**
 * Abstract class representing a command.
 * Each command should extend this class and implement the handler method.
 * The name of the command is provided during initialization and cannot be changed.
 * The handler method processes the command with the provided argument and connection state.
 */
@AllArgsConstructor
public abstract class Command {

    public final String name;

    /**
     * Handles the command with the provided argument and connection state.
     * @param args the argument for the command
     * @param state the connection state
     * @param controlConnection the control connection manager
     * @param dataConnection the data connection manager
     * @return the updated connection state
     */
    public abstract ConnectionState handler(String args, ConnectionState state,
                                            ControlConnection controlConnection, DataConnection dataConnection) throws CannotCloseDataConnection;
}