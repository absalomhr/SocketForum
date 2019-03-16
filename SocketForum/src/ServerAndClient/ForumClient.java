package ServerAndClient;

import DTO.Option;
import DTO.Post;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Absalom Herrera
 */
public class ForumClient {

    private Socket so;
    private int port;
    private String host;
    private ObjectOutputStream oosToServer;
    private ObjectInputStream oisFromServer;

    public ForumClient() {
        host = "127.0.0.1";
        port = 1234;
        try{
            // Server instance for all the duration of the connection
            so = new Socket(host, port);
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());
            ObjectInputStream oisFromServer = new ObjectInputStream(so.getInputStream());
        }catch (Exception ex){
            System.err.println("CL CONSTRUCTOR ERROR");
            ex.printStackTrace();
        }
    }

    public void createPostWithoutImage(Post p) {
        try {
            // title, msg, user, topic and date can't be empty strings (null)
            // that must be checked in the GUI. But an image it's not required
            if (p.getPath_img() == null) {
                System.out.println("Sending post without image to server");
                // Sending option to server: 0 = create post without image
                oosToServer.writeObject(new Option(0));
                // Sendig the post to be uploaded
                oosToServer.writeObject(p);
                // Since we dont need anything else, we close the stream and connection
                oosToServer.close();
                so.close();
            } else {
                // TODO: send post that includes an image
            }
        } catch (Exception ex) {
            System.err.println("CREATE POST CL ERROR");
            ex.printStackTrace();
        }
    }
    
    public List getAllPost(){
        try{
            // Option: 1 = get all posts
            oosToServer.writeObject(new Option(1));
            List l = null;
            l = (List) oisFromServer.readObject();
             return l;
        }catch (Exception ex){
            System.err.println("GET ALL POST CL ERROR");
            ex.printStackTrace();
        }
        return null;
    }
}
