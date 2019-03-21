package ServerAndClient;

import DTO.Comment;
import DTO.Option;
import DTO.Post;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Absalom Herrera
 */
public class ForumClient {

    private int port;
    private String host;
    private String clientRoute = "C:\\Users\\elpat\\Documents\\ClientForum";

    public ForumClient() {
        host = "127.0.0.1";
        port = 1025;
    }

    public void createPost(Post p) {
        try { 
            Socket so = new Socket(host, port);
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());

            // Sending option to server: 0 = create post
            oosToServer.writeObject(new Option(0));
            // title, msg, user, topic and date can't be empty strings (null)
            // that must be checked in the GUI. But an image it's not required
            
            // Receiving port number for image upload
            ObjectInputStream oisFromServer = new ObjectInputStream(so.getInputStream());
            Option op = (Option) oisFromServer.readObject();
            
            // System.out.println("port received:  " + op.getOption());
            
            // Sendig the post to be uploaded
            oosToServer.writeObject(p);
            
            if (p.getPath_img() != null) {
                // Post without image
                Socket so1 = new Socket(host, op.getOption());
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
                System.out.print("\n");
                disFromFile.close();
                dosToServer.close();
                so1.close();
            }
        } catch (Exception ex) {
            System.err.println("CREATE POST CL ERROR");
            ex.printStackTrace();
        }
    }
    
    public void createComment (Comment c){
        try{
            Socket so = new Socket(host, port);
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());

            // Sending option to server: 2 = create comment
            oosToServer.writeObject(new Option(2));
            
            // Sending the comment to the server
            oosToServer.writeObject(c);
        }catch(Exception ex){
            System.err.println("CREATE COMMENT CLIENT ERROR");
            ex.printStackTrace();
        }
    }
    
    public List getAllPost(){
        try{
            Socket so = new Socket(host, port);
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());
            // Option: 1 = get all posts
            oosToServer.writeObject(new Option(1));
            List l = null;
            ObjectInputStream oisFromServer = new ObjectInputStream(so.getInputStream());
            l = (List) oisFromServer.readObject();
            return l;
        }catch (Exception ex){
            System.err.println("GET ALL POST CL ERROR");
            ex.printStackTrace();
        }
        return null;
    }
    
    public void getPostImage (Post p){
        System.out.println("GETTING IMAGE FROM SERVER");
        
        
        try{
            Socket so = new Socket(host, port);
            ObjectOutputStream oosToServer = new ObjectOutputStream(so.getOutputStream());

            // Sending option to server: 0 = create post
            oosToServer.writeObject(new Option(3));
            oosToServer.writeObject(p);
            
            ObjectInputStream oisFromServer = new ObjectInputStream(so.getInputStream());
            Option op = (Option) oisFromServer.readObject(); // Reading available port
            
            Socket imageSo = new Socket(host, op.getOption());
            DataInputStream disFromServer = new DataInputStream(imageSo.getInputStream());
            
            String fileName = disFromServer.readUTF();
            Long fileSize = disFromServer.readLong();
            
            String filePath = clientRoute + "\\" + fileName;
            DataOutputStream dosToFile = new DataOutputStream(new FileOutputStream(filePath));

            long r = 0;
            int n = 0, percent = 0;
            while (r < fileSize) {
                byte[] b = new byte[1500];
                n = disFromServer.read(b);
                dosToFile.write(b, 0, n);
                dosToFile.flush();
                r += n;
                percent = (int) ((r * 100) / fileSize);
                System.out.print("\rRECEIVING: " + percent + "%");
            }
            System.out.print("\n");
            dosToFile.close();
            disFromServer.close();
            imageSo.close();
            p.setPath_img(filePath);
            
        }catch (Exception e){
            System.err.println("GET POST IMAGE CL ERROR");
            e.printStackTrace();
        }
    }
}
