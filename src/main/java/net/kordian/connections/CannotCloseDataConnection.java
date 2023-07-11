package net.kordian.connections;

import java.io.IOException;

/**
 * The type Cannot close data connection.
 */
public class CannotCloseDataConnection extends Exception {
    /**
     * Instantiates a new Cannot close data connection.
     *
     * @param e the e
     */
    public CannotCloseDataConnection(IOException e) {
        super("Error during closing data connection: " + e);
    }
}
