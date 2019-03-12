package ServerAndClient;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Absalom Herrera
 */
public class ForumServer {

    private Socket cl;
    private ServerSocket s;
    private int port;

    public ForumServer() {
        // Creating server
        port = 1234;

        try {
            s = new ServerSocket(port);
            s.setReuseAddress(true);
        } catch (Exception ex) {
            System.err.println("SERVER CONSTRUCTOR ERROR:\n");
            ex.printStackTrace();
        }
    }

    private void connect() {
        try {
            for (;;) {
                System.out.println("\nSERVER ON, WAITING FOR CLIENT CONNECTION");
                // Accepting connetion from client and creating thread
                cl = s.accept();
                (new ServerForumThread(cl)).run();

                // TODO: Keeping track of client connections
            }
        } catch (Exception ex) {
            System.err.println("SERVER CONNECT ERROR");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ForumServer fs = new ForumServer();
        fs.connect();
    }

}
