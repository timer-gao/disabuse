package edu.ssdut.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
public class Major {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    public Major() {
    }

    public Major(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
