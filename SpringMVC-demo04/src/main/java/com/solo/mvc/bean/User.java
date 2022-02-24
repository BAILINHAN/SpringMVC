package com.solo.mvc.bean;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String userName;

    private String passWord;

    private String sex;

    private Integer age;

    public User(Integer id, String userName, String passWord, String sex, Integer age) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.sex = sex;
        this.age = age;
    }
}
