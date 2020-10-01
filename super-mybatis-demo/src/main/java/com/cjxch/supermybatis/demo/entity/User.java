package com.cjxch.supermybatis.demo.entity;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.base.enu.MatchMode;

@Table(value="test_user")
public class User {
    @PrimaryKey
    private String id;
    @Column(matchMode = MatchMode.CENTER_MATCH)
    private String name;
    @Column(updateAnyway = true, matchMode = MatchMode.LE)
    private Integer age;
    @Column(ignored = true)
    private String emailAddress;
    @Column(name="nick_name")
    private String nname;

    public User(){}

    public User(String name, Integer age, String emailAddress, String nname) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
        this.nname = nname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                ", nick_name='" + nname + '\'' +
                '}';
    }
}