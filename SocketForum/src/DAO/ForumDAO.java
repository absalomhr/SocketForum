package DAO;

import DTO.Post;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Absalom Herrera
 */
public class ForumDAO {
    private Connection con;
    private static final String SQL_NEW_POST = "insert into post (message, path_img, user, date, topic, title) values (?, ?, ?, ?, ?, ?)";
    
    public void createPost (Post p) throws SQLException{
        getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_NEW_POST);
            ps.setString(1, p.getMessage());
            ps.setString(2, p.getPath_img());
            ps.setString(3, p.getUser());
            ps.setDate(4, (Date) p.getDate());
            ps.setString(5, p.getTopic());
            ps.setString(6, p.getTitle());
            ps.executeUpdate();
        } finally {
            ps.close();
            con.close();
        }
    }
    
    private void getConnection() {
        String user = "root";
        String pwd = "absalom94";
        String url = "jdbc:mysql://localhost:3306/Post_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        String mySqlDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(mySqlDriver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            System.err.println("GET CONECTION ERROR");
            e.printStackTrace();
        }
    }
}
