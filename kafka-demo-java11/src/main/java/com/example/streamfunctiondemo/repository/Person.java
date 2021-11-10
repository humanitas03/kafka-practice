package com.example.streamfunctiondemo.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="person")
@Getter
@Setter
@JsonDeserialize
@JsonSerialize
@SequenceGenerator(name="SEQ_PERSON",
    allocationSize = 100
)
@EntityListeners(AuditingEntityListener.class)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "SEQ_PERSON")
    private Long id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String phoneNumber;

    @Column(length = 2000)
    private String message;

    @CreatedDate
    @Column(name="created_at")
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp updated_at;

    public static Person of(String name, String gender, int age, String phoneNumber){
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setGender(gender);
        person.setPhoneNumber(phoneNumber);
        return person;
    }
}
