package edu.ssdut.entities;

import javax.persistence.*;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Entity
public class ImageOfQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

    @Basic@Lob
    @Column(nullable = false)
    private byte[] image;

    public ImageOfQuestion() {
    }

    public ImageOfQuestion(Question question, byte[] image) {
        this.question = question;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
