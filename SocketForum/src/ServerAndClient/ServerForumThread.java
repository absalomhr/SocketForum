package ServerAndClient;

import DAO.ForumDAO;
import DTO.Comment;
import DTO.Option;
import DTO.Post;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Absalom Herrera
 */
public class ServerForumThread implements Runnable {

    private Socket cl;
    private ObjectInputStream oisFromClient;
    private ObjectOutputStream ousToClient;
    // Change this according to your needs
    private String serverRoute = "C:\\Users\\elpat\\Documents\\ServerForum";
    // The server will read the option and then execute the needed method
    private Option opt;
    private int port; // port for image uploading

    public ServerForumThread(Socket cl, int port) {
        this.cl = cl;
        this.port = port;
    }

    private void getClientOption() {
        try {
            System.out.println("Getting client option");
            oisFromClient = new ObjectInputStream(cl.getInputStream());
            opt = (Option) oisFromClient.readObject();
            // Here the server calls the required method
            // 0 = create post without image
            // 1 = get all posts
            if (opt.getOption() == 0) {
                createPost();
                oisFromClient.close();
            } else if (opt.getOption() == 1) {
                getAllPost();
            } else if (opt.getOption() == 2){
                createComment();
            }
            oisFromClient.close();
            cl.close();
        } catch (Exception ex) {
            System.err.println("GET CLIENT OPTION ERROR");
            ex.printStackTrace();
        }
    }

    public void createPost() {
        System.out.println("CREATING POST");
        ForumDAO fdao = new ForumDAO();
        Post p = new Post();
        try {
            ObjectOutputStream oosToClient = new ObjectOutputStream(cl.getOutputStream());
            oosToClient.writeObject(new Option(port));
            
            p = (Post) oisFromClient.readObject();
            if (p.getPath_img() == null) { // No image
                fdao.createPost(p);
            } else { // Image
                if (!Files.isDirectory(Paths.get(serverRoute + "\\" + p.getDate().toString()))) { // Check if directory exists
                    new File(serverRoute + "\\" + p.getDate().toString()).mkdir();
                }
                ServerSocket imageSocket = new ServerSocket(port);
                imageSocket.setReuseAddress(true);
                Socket cl2 = imageSocket.accept();
                
                DataInputStream disFromCl = new DataInputStream(cl2.getInputStream());

                long fileSize = disFromCl.readLong();
                String fileName = disFromCl.readUTF();
                String filePath = serverRoute + "\\" + p.getDate().toString() + "\\" + fileName;
                DataOutputStream dosToFile = new DataOutputStream(new FileOutputStream(filePath));

                long r = 0;
                int n = 0, percent = 0;
                while (r < fileSize) {
                    byte[] b = new byte[1500];
                    n = disFromCl.read(b);
                    dosToFile.write(b, 0, n);
                    dosToFile.flush();
                    r += n;
                    percent = (int) ((r * 100) / fileSize);
                    System.out.print("\rRECEIVING: " + percent + "%");
                }
                dosToFile.close();
                disFromCl.close();
                cl2.close();
                imageSocket.close();
                p.setPath_img(filePath);
                fdao.createPost(p);
            }

        } catch (Exception ex) {
            System.err.println("CREATE POST SERVER ERROR");
            ex.printStackTrace();
        }
    }
    
    public void createComment (){
        System.out.println("CREATING COMMENT");
        ForumDAO fdao = new ForumDAO();
        Comment c = new Comment();
        try{
            c = (Comment) oisFromClient.readObject();
            fdao.createComment(c);
        }catch (Exception ex){
            System.err.println("CREATE COMMENT ERROR");
            ex.printStackTrace();
        }
    }
    
    public void getAllPost() {
        System.out.println("Getting all posts server thread");
        ForumDAO fdao = new ForumDAO();
        List l = null;
        try {
            l = fdao.getAllPost();
            if (l != null) {
                System.out.println("Posts found in server");
                for (Object o : l) {
                    Post p = (Post) o;
                    System.out.println(p.getIdpost());
                    List coms = getComments(p);
                    p.setComments(coms);
                }
            }
            ousToClient = new ObjectOutputStream(cl.getOutputStream());
            ousToClient.writeObject(l);
        } catch (Exception ex) {
            System.err.println("GET ALL POST SERVER ERROR");
            ex.printStackTrace();
        }
    }

    public List getComments(Post p) {
        List l = null;
        ForumDAO fdao = new ForumDAO();
        try {
            l = fdao.getComments(p);
            return l;
        } catch (Exception ex) {
            System.err.println("GET COMMENT SERVER ERROR");
            ex.printStackTrace();
        }
        return l;
    }

    @Override
    public void run() {
        System.out.println("IN THREAD: CL ADDR: " + cl.getInetAddress() + " CL PORT: " + cl.getPort());
        getClientOption();
        System.err.println("EXITING THREAD");
    }

}
