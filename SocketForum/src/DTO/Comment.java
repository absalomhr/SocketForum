package DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Absalom Herrera
 */
public class Comment implements Serializable {

    private int idComment, id_post;
    private String message, user;
    private Date date;

    public Comment() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" + "idComment=" + idComment + ", id_post=" + id_post + ", message=" + message + ", user=" + user + ", date=" + date + '}';
    }

}
