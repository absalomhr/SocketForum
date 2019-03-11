/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerAndClient;

import DAO.ForumDAO;
import DTO.Post;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
