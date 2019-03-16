package ServerAndClient;

import DTO.Option;
import DTO.Post;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private ObjectOutputStream oosToServer = null;
    private ObjectInputStream oisFromServer = null;

    public ForumClient() {
        host = "127.0.0.1";
        port = 1234;
        try{
            // Server instance for all the duration of the connection
            so = new Socket(host, port);
            //ObjectInputStream oisFromServer = new ObjectInputStream(so.getInputStream());
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());
            if (oosToServer != null)
                System.out.println("FLUJO CREADO");
            
        }catch (Exception ex){
            System.err.println("CL CONSTRUCTOR ERROR");
            ex.printStackTrace();
        }
    }

    public void createPost(Post p) {
        if (oosToServer != null)
                System.out.println("FLUJO METODO NICE");
        if (oosToServer == null)
                System.out.println("FLUJO METODO NULO");
        try {
            // title, msg, user, topic and date can't be empty strings (null)
            // that must be checked in the GUI. But an image it's not required

            // Sending option to server: 0 = create post
            oosToServer.writeObject(new Option(0));
            // Sendig the post to be uploaded
            oosToServer.writeObject(p);
            if (p.getPath_img() != null) {
                
                Socket so1 = new Socket(host, port + 1);
                DataOutputStream dosToServer = new DataOutputStream(so1.getOutputStream());
                File f = new File(p.getPath_img());
                dosToServer.writeLong(f.length());
                dosToServer.writeUTF(f.getName());
                System.out.println("SENDING IMAGE FILE : " + f.getName());
                long sent = 0;
                int percent = 0, n = 0;
                DataInputStream disFromFile = new DataInputStream(new FileInputStream(f.getAbsolutePath()));
                while (sent < f.length()) {
                    byte[] b = new byte[1500];
                    n = disFromFile.read(b);
                    dosToServer.write(b, 0, n);
                    dosToServer.flush();
                    sent += n;
                    percent = (int) ((sent * 100) / f.length());
                    System.out.print("\rSENT: " + percent + " %");
                }
                disFromFile.close();
                dosToServer.close();
                so1.close();
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
