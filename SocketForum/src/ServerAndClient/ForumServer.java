package ServerAndClient;

import DAO.ForumDAO;
import DTO.Post;


/**
 *
 * @author Absalom Herrera
 */
public class ForumServer {
    public void createPost(Post p){
        ForumDAO fdao = new ForumDAO();
        try {
            fdao.createPost(p);
        } catch (Exception ex) {
            System.err.println("CREATE POST SERVER ERROR");
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ForumServer fs = new ForumServer();
        Post p = new Post();
        p.setMessage("Hola");
        fs.createPost(p);
    }
    
}
