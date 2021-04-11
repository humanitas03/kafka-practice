/**
 * ===============================================================
 * File name : PersonProcessException.java
 * Created by injeahwang on 2021-04-10
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.exception;

import com.example.kafkamultichannelpractice.entity.Person;
import lombok.Getter;

@Getter
public class PersonProcessException extends RuntimeException{

    private Person person;
    private String errorCode;


    public PersonProcessException(String message){
        super(message);
    }

    public PersonProcessException(String errorMessage, String errorCode){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public PersonProcessException(Throwable throwable){
        super(throwable);
    }

    public PersonProcessException(String message, Person person){
        super(message);
        this.person=person;
    }

    public PersonProcessException(Person person, Throwable throwable){
        super(throwable);
        this.person=person;
    }

    public PersonProcessException(String message, Person person, Throwable throwable){
        super(message,throwable);
        this.person=person;
    }

    public PersonProcessException(Person person){
        super();
        this.person=person;
    }


}
