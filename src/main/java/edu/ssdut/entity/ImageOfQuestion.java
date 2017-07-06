package edu.ssdut.entity;

import org.hibernate.type.ImageType;

import javax.persistence.*;
import java.awt.*;
import java.awt.image.ImageConsumer;

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
    private Image image;

    public ImageOfQuestion() {
    }

    public ImageOfQuestion(Question question, Image image) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
