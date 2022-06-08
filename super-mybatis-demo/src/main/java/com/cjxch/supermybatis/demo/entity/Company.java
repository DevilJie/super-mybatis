package com.cjxch.supermybatis.demo.entity;

import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.annotation.Table;

import java.io.Serializable;

@Table(value="company")
public class Company implements Serializable {
    @PrimaryKey
    private String id;

    private String name;

    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}