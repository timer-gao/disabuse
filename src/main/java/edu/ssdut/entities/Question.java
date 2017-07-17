package edu.ssdut.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private Long views;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonBackReference
    private Section section;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_tag",
                joinColumns = {@JoinColumn(name = "question_id")},
                inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    @JsonBackReference
    private Set<ImageOfQuestion> imageOfQuestionSet;

    public Question() {
    }

    public Question(String title, String description, Long views, Date createDate, User user, Section section, Set<Tag> tags, Set<ImageOfQuestion> imageOfQuestionSet) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.createDate = createDate;
        this.user = user;
        this.section = section;
        this.tags = tags;
        this.imageOfQuestionSet = imageOfQuestionSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<ImageOfQuestion> getImageOfQuestionSet() {
        return imageOfQuestionSet;
    }

    public void setImageOfQuestionSet(Set<ImageOfQuestion> imageOfQuestionSet) {
        this.imageOfQuestionSet = imageOfQuestionSet;
    }
}
