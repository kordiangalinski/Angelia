/**
 * 
 */
package net.kordian.angelia.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author Kordian Galiński
 * @created 08/03/2023
 */

/**
 * A simple FTP server that listens on port 21 and handles incoming client connections.
 */
public class FTPServer {
    private static final int NETWORK_PORT = 21;
    
    private final ServerSocketFactory serverSocketFactory;
    private final ExecutorService executorService;
    
    private ServerSocket serverSocket;
    
    private boolean isServerRunning = false;

    /**
     * Constructs an instance of the FTPServer class.
     * @param serverSocketFactory the factory to use for creating server sockets
     * @param executorService the executor service to use for running client handlers
     */
    public FTPServer(ServerSocketFactory serverSocketFactory, ExecutorService executorService) {
        this.serverSocketFactory = serverSocketFactory;
        this.executorService = executorService;
    }

    /**
     * Initializes the server socket
     * @return ServerSocket
     * @throws IOException if an I/O error occurs while initializing the server socket
     */
    private ServerSocket initSocket() throws IOException {
        serverSocket = serverSocketFactory.createServerSocket(NETWORK_PORT);
        System.out.println("FTP Server started listening on port " + NETWORK_PORT);
        isServerRunning = true;
    	return serverSocket;
    }
    
    /**
     * Starts listening for incoming client connections.
     * @throws IOException if an I/O error occurs while listening on the server socket
     */
    public void run() throws IOException {
        try {
        	this.serverSocket = initSocket();
        	
            while (this.isServerRunning) {
                Socket clientSocket = serverSocket.accept();
                FTPClientHandler clientHandler = new FTPClientHandler(clientSocket);
                executorService.submit(clientHandler);
            }

        } catch (IOException e) {
            System.err.println("FTP server stopped due to error: " + e.getMessage());
            throw e;
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * Stops the server from accepting any further client connections.
     */
    public void stop() {
        isServerRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error while stopping FTP server: " + e.getMessage());
        }
    }
}
