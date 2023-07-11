package net.kordian.server;

import net.kordian.server.exceptions.ServerSocketNotInitialized;
import net.kordian.server.exceptions.UnableToBindServerSocket;
import net.kordian.server.exceptions.UnableToEstablishConnection;

/**
 * The type Ftp server.
 */
public class FTPServer {
    private final Listener listener = new Listener();

    /**
     * Run.
     */
    public void run() {
        try {
            listener.init();
            try {
                listener.run();
            } catch (ServerSocketNotInitialized | UnableToEstablishConnection e) {
                listener.close();
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | UnableToBindServerSocket e) {
            throw new RuntimeException(e);
        }
    }
}
