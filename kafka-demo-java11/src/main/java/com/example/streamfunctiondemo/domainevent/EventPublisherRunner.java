/**
 * ===============================================================
 * File name : EventPublisherRunner.java
 * Created by injeahwang on 2021-08-01
 * ===============================================================
 */
package com.example.streamfunctiondemo.domainevent;

import com.example.streamfunctiondemo.repository.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherRunner implements ApplicationRunner {

    private final ApplicationContext applicationContext;

    public EventPublisherRunner( ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Person person = new Person();

        person.setId(System.currentTimeMillis());
        person.setName("Event Person");
        person.setGender("G");
        person.setPhoneNumber("00000000");
        person.setAge(10);

        PersonPublishedEvent personPublishedEvent = new PersonPublishedEvent(person);

        applicationContext.publishEvent(personPublishedEvent);
    }
}
