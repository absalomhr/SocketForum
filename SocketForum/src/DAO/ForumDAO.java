package DAO;

import DTO.Comment;
import DTO.Post;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Absalom Herrera
 */
public class ForumDAO {
    private Connection con;
    private static final String SQL_INSERT_POST = "insert into post (message, path_img, user, date, topic, title) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL_POST = "select * from post";
    private static final String SQL_SELECT_COMMENTS = "select * from comment where id_post = ?";
    private static final String SQL_INSERT_COMMENT = "insert into Comment (message, id_post, user, date) values (?, ?, ?, ?)";
    
    public void createPost (Post p) throws SQLException{
        getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_INSERT_POST);
            ps.setString(1, p.getMessage());
            ps.setString(2, p.getPath_img());
            ps.setString(3, p.getUser());
            ps.setDate(4, p.getDate());
            ps.setString(5, p.getTopic());
            ps.setString(6, p.getTitle());
            ps.executeUpdate();
        } finally {
            ps.close();
            con.close();
        }
    }
    
    public void createComment(Comment c) throws SQLException {
        getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_INSERT_COMMENT);
            ps.setString(1, c.getMessage());
            ps.setInt(2, c.getId_post());
            ps.setString(3, c.getUser());
            ps.setDate(4, c.getDate());
            ps.executeUpdate();
        } finally {
            ps.close();
            con.close();
        }
    }
    
    
    public List getAllPost () throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        getConnection();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL_POST);
            rs = ps.executeQuery();
            List results = getPostResults (rs);
            if (results.size() > 0) {
                return results;
            } else {
                return null;
            }
        } finally {
            rs.close();
            ps.close();
            con.close();
        }
    }
    
    public List getComments (Post p) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        getConnection();
        try {
            ps = con.prepareStatement(SQL_SELECT_COMMENTS);
            ps.setInt(1, p.getIdpost());
            rs = ps.executeQuery();
            List results = getCommentResults (rs);
            if (results.size() > 0) {
                return results;
            } else {
                return null;
            }
        } finally {
            //rs.close();
            ps.close();
            con.close();
        }
    }
    
    private List getPostResults (ResultSet rs) throws SQLException{
        List results = new ArrayList();
        while (rs.next()){
            Post p = new Post();
            p.setIdpost(rs.getInt("id_post"));
            p.setDate(rs.getDate("date"));
            p.setMessage(rs.getString("message"));
            p.setPath_img(rs.getString("path_img"));
            p.setUser(rs.getString("user"));
            p.setTopic(rs.getString("topic"));
            p.setTitle(rs.getString("title"));
            results.add(p);
        }
        return results;
    }
    
    private List getCommentResults (ResultSet rs) throws SQLException{
        List results = new ArrayList();
        while (rs.next()){
            Comment c = new Comment();
            c.setDate(rs.getDate("date"));
            c.setIdComment(rs.getInt("idComment"));
            c.setId_post(rs.getInt("id_post"));
            c.setMessage(rs.getString("message"));
            c.setUser(rs.getString("user"));
            System.out.println(c.toString());
            results.add(c);
        }
        return results;
    }
    
    private void getConnection() {
        String user = "root";
        //String pwd = "absalom94";
        //String pwd = "root";
        String pwd = "ale970807";
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
