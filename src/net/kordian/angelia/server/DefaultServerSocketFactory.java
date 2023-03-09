package net.kordian.angelia.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Kordian Galiński
 *
 */
/**
 * A default implementation of the ServerSocketFactory interface.
 */
public class DefaultServerSocketFactory implements ServerSocketFactory {

    /**
     * Creates a new ServerSocket object that is bound to the specified port.
     *
     * @param port the port number to listen on
     * @return the new ServerSocket object
     * @throws IOException if there is an error creating the ServerSocket object
     */
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
