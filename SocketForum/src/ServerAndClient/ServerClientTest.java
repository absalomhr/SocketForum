package ServerAndClient;

import DTO.Comment;
import DTO.Post;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Absalom Herrera
 */
public class ServerClientTest {
    public static void main(String[] args) {
        
        String imagePath = "C:\\Users\\elpat\\Documents\\Mim\\frog.jpg";
        Post p = new Post();
        
        p.setMessage("mensagge1");
        p.setPath_img(imagePath);
        p.setTitle("titulo");
        p.setTopic("tema");
        p.setUser("usuario");
        
        Calendar cal = Calendar.getInstance();

        // set Date portion to January 1, 1970
        cal.set(cal.YEAR, 1970);
        cal.set(cal.MONTH, cal.JANUARY);
        cal.set(cal.DATE, 1);

        cal.set(cal.HOUR_OF_DAY, 0);
        cal.set(cal.MINUTE, 0);
        cal.set(cal.SECOND, 0);
        cal.set(cal.MILLISECOND, 0);

        java.sql.Date jsqlD = new java.sql.Date(cal.getTime().getTime());
        
        p.setDate(jsqlD);
        ServerClientTest n = new ServerClientTest();
       // n.sendpost(p);

        
        p.setPath_img("C:\\Users\\elpat\\Documents\\Mim\\vecindad.jpg");
        p.setMessage("mensagge2");
       //n.sendpost(p);
        
        p.setPath_img(null);
        p.setMessage("mensagge3");
        //n.sendpost(p);
        
        Comment co = new Comment();
        co.setMessage("coment shido1");
        co.setId_post(1);
        n.sendComment(co);
        
        co.setMessage("coment shido2");
        co.setId_post(1);
        n.sendComment(co);
        
        co.setMessage("coment shido3");
        co.setId_post(1);
        n.sendComment(co);
        
        List l = n.getPost();
        
        if (l != null){
            for (Object o : l) {
                Post po = (Post) o;
                System.out.println(po.getIdpost());
                System.out.println(po.getMessage());
                if (po.getComments() != null){
                    List com = po.getComments();
                    for (Object ob : com){
                        Comment c = (Comment) ob;
                        System.out.println(c.getMessage());
                    }
                }
            }
        }      
        
        
    }
    
    public void sendpost(Post p){
        ForumClient fctest = new ForumClient();
        fctest.createPost(p);
    }
    
    public List getPost (){
        ForumClient fctest = new ForumClient();
        List l = fctest.getAllPost();
        
        return l;
    }
    
    public void sendComment (Comment c){
        ForumClient fctest = new ForumClient();
        fctest.createComment(c);
    }
}
