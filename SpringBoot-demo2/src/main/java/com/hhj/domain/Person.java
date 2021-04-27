package com.hhj.domain;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Data
@ToString
@ApiModel("人 的相关信息")
public class Person {
    private String username;
    private int age;

    private Date birth;
    private Pet pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                username.equals(person.username) &&
                birth.equals(person.birth) &&
                pet.equals(person.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, age, birth, pet);
    }
}
