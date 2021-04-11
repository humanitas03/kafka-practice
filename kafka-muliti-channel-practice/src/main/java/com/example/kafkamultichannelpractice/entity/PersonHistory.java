/**
 * ===============================================================
 * File name : PersonHistory.java
 * Created by injeahwang on 2021-04-10
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="PERSON_HIST")
@NoArgsConstructor
@AllArgsConstructor
public class PersonHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String userName;

    @Column
    private int age;

    @Column
    private String phoneNumber;

    @Column
    private String gender;

    @Column
    private String sendTime;

    @Column
    private String topic;

    @Column(length = 1000)
    private String errorCause;
}
