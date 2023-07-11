package net.kordian;

import net.kordian.server.FTPServer;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        FTPServer ftpServer = new FTPServer();
        ftpServer.run();
    }
}