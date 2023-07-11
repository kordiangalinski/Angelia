package net.kordian.server.exceptions;

/**
 * The type Server socket not initialized.
 */
public class ServerSocketNotInitialized extends Exception {
    /**
     * Instantiates a new Server socket not initialized.
     */
    public ServerSocketNotInitialized() {
        super("Server socket is not initialized. Call init() method first.");
    }
}
