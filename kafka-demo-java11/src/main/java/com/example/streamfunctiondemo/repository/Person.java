package com.example.streamfunctiondemo.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="person")
@Getter
@Setter
@JsonDeserialize
@JsonSerialize
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String phoneNumber;

    public static Person of(String name, String gender, int age, String phoneNumber){
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setGender(gender);
        person.setPhoneNumber(phoneNumber);
        return person;
    }
}
