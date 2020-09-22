package com.hsj.supermybatis.demo.entity;

import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.annotation.Table;

@Table(value="test_user")
public class User {
    @PrimaryKey
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String nickName;

    public User(){}

    public User(String id, String name, Integer age, String email, String nickName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.nickName = nickName;
    }
    public User(String name, Integer age, String email, String nickName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.nickName = nickName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}