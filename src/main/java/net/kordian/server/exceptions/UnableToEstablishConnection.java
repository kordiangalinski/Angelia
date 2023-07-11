package net.kordian.server.exceptions;

import java.io.IOException;

/**
 * The type Unable to establish connection.
 */
public class UnableToEstablishConnection extends Throwable {
    /**
     * Instantiates a new Unable to establish connection.
     *
     * @param e the e
     */
    public UnableToEstablishConnection(IOException e) {
        super("Unable to establish connection with the client: " + e.getMessage());
        e.printStackTrace();
    }
}
