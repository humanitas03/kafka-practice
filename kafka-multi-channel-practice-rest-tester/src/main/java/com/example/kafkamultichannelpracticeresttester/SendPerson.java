/**
 * ===============================================================
 * File name : SendPerson.java
 * Created by injeahwang on 2021-08-01
 * ===============================================================
 */
package com.example.kafkamultichannelpracticeresttester;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
@JsonSerialize
public class SendPerson {

    private Long id;

    private String name;
    private String gender;

    private int age;
    private String phoneNumber;
    private String message;

    public static SendPerson of(String name, String gender, int age, String phoneNumber, String message){
        SendPerson person = new SendPerson();
        person.setName(name);
        person.setAge(age);
        person.setGender(gender);
        person.setPhoneNumber(phoneNumber);
        person.setMessage(message);
        return person;
    }
}
