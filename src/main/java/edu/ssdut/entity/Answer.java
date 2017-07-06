package edu.ssdut.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Entity
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    @ManyToOne
    @JoinColumn(name = "re_answer_id")
    @JsonBackReference
    private Answer reAnswer;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private Long views;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    @JsonBackReference
    private Set<Vote> votes;

    public Answer() {
    }

    public Answer(User user, Question question, Answer reAnswer, String description, Date createDate, Set<Vote> votes) {
        this.user = user;
        this.question = question;
        this.reAnswer = reAnswer;
        this.description = description;
        this.createDate = createDate;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getReAnswer() {
        return reAnswer;
    }

    public void setReAnswer(Answer reAnswer) {
        this.reAnswer = reAnswer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
