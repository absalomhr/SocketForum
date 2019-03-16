/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerAndClient;

import DTO.Post;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Absalom Herrera
 */
public class ServerClientTest {
    public static void main(String[] args) {
        
        String imagePath = "C:\\Users\\elpat\\Documents\\Mim\\frog.jpg";
        Post p = new Post();
        
        p.setMessage("mensagge");
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
        
        ForumClient fctest = new ForumClient();
        fctest.createPost(p);
        //fctest.createPost(p);
    }
}
