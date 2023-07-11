package net.kordian.connections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * The type Data connection.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataConnection {
    private ServerSocket dataSocket;
    private Socket dataConnection;
    private PrintWriter dataOut;
    private BufferedReader dataIn;
    /**
     * The Data port.
     */
    final int dataPort = new DataPortManager().findAvailablePort();
    private DataTransferType dataTransferType = DataTransferType.ASCII;

    /**
     * Send to client.
     *
     * @param data the data
     */
    public void sendToClient(String data) {
        dataOut.println(data);
    }

    /**
     * Open connection.
     *
     * @param mode       the mode
     * @param clientPort the client port
     * @param ipAddress  the ip address
     */
    public void openConnection(DataTransferMode mode, int clientPort, String... ipAddress) {
        try {
            switch (mode) {
                case PASSIVE -> {
                    dataSocket = new ServerSocket(clientPort);
                    dataConnection = dataSocket.accept();
                    dataOut = new PrintWriter(dataConnection.getOutputStream(), true);
                }
                case ACTIVE -> {
                    dataConnection = new Socket(Arrays.toString(ipAddress), clientPort);
                    dataOut = new PrintWriter(dataConnection.getOutputStream(), true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection.
     *
     * @throws CannotCloseDataConnection the cannot close data connection
     */
    public void closeConnection() throws CannotCloseDataConnection {
        try {
            if (dataOut != null) {
                dataOut.flush();
                dataOut.close();
            }
            if (dataIn != null) {
                dataIn.close();
            }
            if (dataConnection != null) {
                dataConnection.close();
            }
            if (dataSocket != null && !dataSocket.isClosed()) {
                dataSocket.close();
            }
        } catch (IOException e) {
            throw new CannotCloseDataConnection(e);
        } finally {
            dataConnection = null;
            dataSocket = null;
            dataOut = null;
            dataIn = null;
        }
    }

    /**
     * Send file boolean.
     *
     * @param file the file
     * @param type the type
     * @return the boolean
     */
    public boolean sendFile(File file, DataTransferType type) {
        return switch (type) {
            case BINARY -> sendBinaryFile(file);
            case ASCII -> sendAsciiFile(file);
        };
    }

    private boolean sendBinaryFile(File file) {
        boolean success = true;

        try {
            BufferedOutputStream out = new BufferedOutputStream(this.dataConnection.getOutputStream());
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    private boolean sendAsciiFile(File file) {
        boolean success = true;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            PrintWriter out = new PrintWriter(this.dataConnection.getOutputStream());

            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * Store file boolean.
     *
     * @param file the file
     * @param type the type
     * @return the boolean
     */
    public boolean storeFile(File file, DataTransferType type) {
        return switch (type) {
            case BINARY -> storeBinaryFile(file);
            case ASCII -> storeAsciiFile(file);
        };
    }

    private boolean storeBinaryFile(File file) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
             BufferedInputStream in = new BufferedInputStream(this.dataConnection.getInputStream())) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean storeAsciiFile(File file) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.dataConnection.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new FileOutputStream(file))) {

            String fileLine;
            while ((fileLine = in.readLine()) != null) {
                out.println(fileLine);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}