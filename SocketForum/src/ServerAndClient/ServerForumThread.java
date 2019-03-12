package ServerAndClient;

import DAO.ForumDAO;
import DTO.Option;
import DTO.Post;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Absalom Herrera
 */
public class ServerForumThread implements Runnable {

    private Socket cl;
    private ObjectInputStream oisFromClient;
    // The server will read the option and then execute the needed method
    private Option opt;

    public ServerForumThread(Socket cl) {
        this.cl = cl;
    }

    private void getClientOption() {
        try {
            System.out.println("Getting client option");
            oisFromClient = new ObjectInputStream(cl.getInputStream());
            opt = (Option) oisFromClient.readObject();
            // Here the server calls the required method

            // 0 = create post without image
            if (opt.getOption() == 0) {
                createPostNoImage();
            }
        } catch (Exception ex) {
            System.err.println("GET CLIENT OPTION ERROR");
            ex.printStackTrace();
        }
    }

    public void createPostNoImage() {
        System.out.println("Creating post without image");
        ForumDAO fdao = new ForumDAO();
        Post p = new Post();
        try {
            p = (Post) oisFromClient.readObject();
            fdao.createPost(p);
        } catch (Exception ex) {
            System.err.println("CREATE POST SERVER ERROR");
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("IN THREAD: CL ADDR: " + cl.getInetAddress() + " CL PORT: " + cl.getPort());
        getClientOption();
    }

}
