package edu.ssdut.entity;

import javax.persistence.*;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
public class Province {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    public Province(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Province() {

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
