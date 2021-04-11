/**
 * ===============================================================
 * File name : Person.java
 * Created by injeahwang on 2021-04-11
 * ===============================================================
 */
package com.example.kafkamultichannelpracticeresttester;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@AllArgsConstructor
public class Person {

    private String userName;

    private int age;

    private String phoneNumber;

    private String gender;

    private String sendTime;

    private String topic;

    @SneakyThrows
    @Override
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}
