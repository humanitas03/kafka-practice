/**
 * ===============================================================
 * File name : PersonPublishedEvent.java
 * Created by injeahwang on 2021-08-01
 * ===============================================================
 */
package com.example.streamfunctiondemo.domainevent;

import com.example.streamfunctiondemo.repository.Person;
import org.springframework.context.ApplicationEvent;

public class PersonPublishedEvent extends ApplicationEvent {

    private final Person person;
    public PersonPublishedEvent(Object source) {
        super(source);
        this.person = (Person) source;
    }

    public Person getPerson(){
        return this.person;
    }
}
