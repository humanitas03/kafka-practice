package com.example.streamfunctiondemo.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="person")
@Getter
@Setter
@JsonDeserialize
@JsonSerialize
@Builder
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
}
