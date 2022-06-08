package com.cjxch.supermybatis.demo.entity;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.base.enu.MatchMode;
import com.cjxch.supermybatis.base.enu.PrimaryKeyType;

import java.io.Serializable;
import java.util.Objects;

@Table(value="user_info")
public class User implements Serializable {
    @PrimaryKey(keyType = PrimaryKeyType.SNOWFLAKE)
    private String id;
    @Column(matchMode = MatchMode.CENTER_MATCH)
    private String realName;
    private Integer age;
    @Column(ignored = true)
    private String email;

    public User(){}

    public User(String realName, Integer age, String email) {
        this.realName = realName;
        this.age = age;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(realName, user.realName) &&
                Objects.equals(age, user.age) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, realName, age, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + realName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}