package edu.ssdut.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ssdut.compositeID.ConcernedId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
@IdClass(ConcernedId.class)
public class Concerned implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "concerned_id")
    @JsonBackReference
    private User concernedUser;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public Concerned() {
    }

    public Concerned(User user, User concernedUser, Date createDate) {
        this.user = user;
        this.concernedUser = concernedUser;
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getConcernedUser() {
        return concernedUser;
    }

    public void setConcernedUser(User concernedUser) {
        this.concernedUser = concernedUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
