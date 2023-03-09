package net.kordian.angelia.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Kordian Galiński
 *
 */
/**
 * An interface for creating instances of ServerSocket objects.
 */
public interface ServerSocketFactory {

    /**
     * Creates a new ServerSocket object that is bound to the specified port.
     *
     * @param port the port number to listen on
     * @return the new ServerSocket object
     * @throws IOException if there is an error creating the ServerSocket object
     */
    ServerSocket createServerSocket(int port) throws IOException;
}