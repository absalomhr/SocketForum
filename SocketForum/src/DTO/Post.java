package DTO;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;


/**
 *
 * @author Absalom Herrera
 */
public class Post implements Serializable {

    private String user, message, path_img, topic, title;
    private Date date;
    private int idpost;
    private List comments;

    public Post() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath_img() {
        return path_img;
    }

    public void setPath_img(String path_img) {
        this.path_img = path_img;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdpost() {
        return idpost;
    }

    public void setIdpost(int idpost) {
        this.idpost = idpost;
    }

    public List getComments() {
        return comments;
    }

    public void setComments(List comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" + "user=" + user + ", message=" + message + ", path_img=" + path_img + ", topic=" + topic + ", title=" + title + ", date=" + date + ", idpost=" + idpost + ", comments=" + comments + '}';
    }

    
}
