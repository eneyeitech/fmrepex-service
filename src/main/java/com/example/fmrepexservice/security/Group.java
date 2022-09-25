package com.example.fmrepexservice.security;

import com.example.fmrepexservice.usermanagement.business.User;

import java.util.Set;

public class Group{


    private long id;


    private String code;
    private String name;

    public Group() {
    }

    public Group(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private Set<User> users;
}

