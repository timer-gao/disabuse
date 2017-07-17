package edu.ssdut.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ssdut.enums.EduDegreeType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "university_id",nullable = false)
    @JsonBackReference
    private University university;

    @ManyToOne
    @JoinColumn(name = "major_id",nullable = false)
    @JsonBackReference
    private Major major;
    private EduDegreeType degree;
    @Temporal(TemporalType.DATE)
    private Date begin;
    @Temporal(TemporalType.DATE)
    private Date end;

    public Education() {
    }

    public Education(University university, Major major, EduDegreeType eduDegree, Date begin, Date end) {
        this.university = university;
        this.major = major;
        this.degree = eduDegree;
        this.begin = begin;
        this.end = end;
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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public EduDegreeType getEduDegree() {
        return degree;
    }

    public void setEduDegree(EduDegreeType eduDegree) {
        this.degree = eduDegree;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
