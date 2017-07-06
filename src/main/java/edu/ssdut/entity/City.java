package edu.ssdut.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
public class City {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id",nullable = false)
    @JsonBackReference
    private Province province;

    public City() {
    }

    public City(Long id, String name, Province province) {

        this.id = id;
        this.name = name;
        this.province = province;
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
