package com.hhj.shiro;

import lombok.Data;

import java.io.Serializable;


@Data
public class MyPorfile implements Serializable {

    int id;
    String name;
    int age;
    String email;
}
