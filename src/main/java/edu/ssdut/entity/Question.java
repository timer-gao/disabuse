package edu.ssdut.entity;

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
    private String description;

    private Long views;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

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

    public Question(String description, Date createDate, Section section, Set<Tag> tags, Set<ImageOfQuestion> imageOfQuestionSet) {
        this.description = description;
        this.createDate = createDate;
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
