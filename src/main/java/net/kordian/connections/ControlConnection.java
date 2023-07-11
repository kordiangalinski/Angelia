package net.kordian.connections;

import java.io.*;
import java.net.Socket;

/**
 * The type Control connection.
 */
public class ControlConnection {
    private final Socket controlConnection;
    private PrintWriter controlOut;
    private BufferedReader controlIn;

    /**
     * Instantiates a new Control connection.
     *
     * @param socket the socket
     * @throws IOException the io exception
     */
    public ControlConnection(Socket socket) throws IOException {
        this.controlConnection = socket;
        try {
            this.controlIn = new BufferedReader(new InputStreamReader(this.controlConnection.getInputStream()));
            this.controlOut = new PrintWriter(this.controlConnection.getOutputStream(), true);
        } catch (IOException e) {
            this.controlConnection.close();
        }
    }

    /**
     * Send to client.
     *
     * @param message the message
     */
    public void sendToClient(String message) {
        this.controlOut.println(message);
    }

    /**
     * Gets client input.
     *
     * @return the client input
     * @throws IOException the io exception
     */
    public String getClientInput() throws IOException {
        return controlIn.readLine();
    }

    /**
     * Stop.
     *
     * @throws IOException the io exception
     */
    public void stop() throws IOException {
        this.controlIn.close();
        this.controlOut.close();
        this.controlConnection.close();
    }
}
