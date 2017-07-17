package edu.ssdut.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Gaomj on 2017/7/2.
 */
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 32)
    private String nickname;
    @Column(nullable = false,length = 16)
    private String password;
    private Character sex;
    @Column(nullable = false,unique = true)
    private String email;
    private String signature;
    @Column(columnDefinition = "text")
    private String introduction;
    @Column(length = 3)
    private String avatar;
    private Long views;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    @JsonBackReference
    private Profession profession;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonBackReference
    private City city;

    //这里配置关系，并且确定关系维护端和被维护端。mappBy表示关系被维护端，只有关系端有权去更新外键。
    // 这里还有注意OneToMany默认的加载方式是赖加载。当看到设置关系中最后一个单词是Many，那么该加载默认为懒加载
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private Set<Education> educations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonBackReference
    private Set<Concerned> concerns;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "interest",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> interests;

    public User() {
    }

    public User(String nickname, String password, Character sex, String email, String signature, String introduction, String avatar, Long views, Profession profession, City city, Set<Education> educations, Set<Concerned> concerns) {
        this.nickname = nickname;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.signature = signature;
        this.introduction = introduction;
        this.avatar = avatar;
        this.views = views;
        this.profession = profession;
        this.city = city;
        this.educations = educations;
        this.concerns = concerns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Concerned> getConcerns() {
        return concerns;
    }

    public void setConcerns(Set<Concerned> concerns) {
        this.concerns = concerns;
    }


    public Set<Education> getEducations() {
        return educations;
    }

    /**
     * 设置educations集合，添加单个education项请选择 public void addEducation(Education education);
     * @param educations
     */
    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    /**
     * 该方法用于向educations中添加education项
     * @param education
     */
    public void addEducation(Education education) {
        education.setUser(this);//用关系维护端来维护关系
        this.educations.add(education);
    }

}
