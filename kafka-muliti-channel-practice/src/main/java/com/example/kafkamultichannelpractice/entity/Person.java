/**
 * ===============================================================
 * File name : Person.java
 * Created by injeahwang on 2021-04-09
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;


@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;

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
        return mapper.writeValueAsString(this);
    }
}
