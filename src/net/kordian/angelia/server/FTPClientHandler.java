/**
 * 
 */
package net.kordian.angelia.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Kordian Galiński
 */

/**
 * Handles incoming client connections to the FTP server.
 */
public class FTPClientHandler implements Runnable {
    private final Socket clientSocket;

    /**
     * Constructs an instance of the FTPClientHandler class for a given client socket.
     * @param clientSocket the client socket to handle
     */
    public FTPClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Handles the client connection by receiving and processing incoming FTP commands.
     */
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println("New client connected: " + clientAddress);

            // TODO: Implement FTP command processing logic

        } catch (IOException e) {
            System.err.println("Error while handling client connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error while closing client socket: " + e.getMessage());
            }
        }
    }
}
