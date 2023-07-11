package net.kordian.connections;

import java.io.IOException;
import java.net.Socket;

/**
 * The type Data port manager.
 */
public class DataPortManager {
    private final int DATA_PORT = 1050;

    /**
     * Find available port int.
     *
     * @return the int
     */
    public int findAvailablePort() {
        int currentPort = DATA_PORT;

        while (!isPortAvailable(currentPort)) {
            currentPort++;
        }

        return currentPort;
    }

    // TODO: Inefficient
    private boolean isPortAvailable(int port) {
        try {
            Socket socket = new Socket("localhost", port);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}