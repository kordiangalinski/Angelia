package net.kordian.server.exceptions;

import java.io.IOException;

/**
 * The type Unable to bind server socket.
 */
public class UnableToBindServerSocket extends Throwable {
    /**
     * Instantiates a new Unable to bind server socket.
     *
     * @param e the e
     */
    public UnableToBindServerSocket(IOException e) {
        super("ServerSocket was unable to bind");
    }
}
