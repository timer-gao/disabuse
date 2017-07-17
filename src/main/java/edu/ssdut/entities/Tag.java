package edu.ssdut.entities;

import javax.persistence.*;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false,length = 20)
    private String description;

    public Tag() {
    }

    public Tag(String description) {
        this.description = description;
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
}
