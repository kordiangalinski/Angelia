package net.kordian.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kordian.connections.ConnectionHandler;
import net.kordian.server.exceptions.ServerSocketNotInitialized;
import net.kordian.server.exceptions.UnableToBindServerSocket;
import net.kordian.server.exceptions.UnableToEstablishConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Listener.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Listener {
    private static final int NETWORK_CONTROL_PORT = 21;
    private ServerSocket serverSocket;

    /**
     * Init.
     *
     * @throws UnableToBindServerSocket the unable to bind server socket
     */
    public void init() throws UnableToBindServerSocket {
        try {
            this.serverSocket = new ServerSocket(NETWORK_CONTROL_PORT);
        } catch (IOException e) {
            throw new UnableToBindServerSocket(e);
        }
    }

    /**
     * Run.
     *
     * @throws ServerSocketNotInitialized  the server socket not initialized
     * @throws UnableToEstablishConnection the unable to establish connection
     */
    public void run() throws ServerSocketNotInitialized, UnableToEstablishConnection {
        if (this.serverSocket == null) {
            throw new ServerSocketNotInitialized();
        }
        System.out.println("Listening on port: " + NETWORK_CONTROL_PORT);
        while (this.serverSocket != null) {
            try {
                Socket client = this.serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                connectionHandler.start();
            } catch (IOException e) {
                throw new UnableToEstablishConnection(e);
            }
        }
    }

    /**
     * Close.
     */
    public void close() {
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (Exception e) {
                this.serverSocket = null;
            }
        }
    }
}
