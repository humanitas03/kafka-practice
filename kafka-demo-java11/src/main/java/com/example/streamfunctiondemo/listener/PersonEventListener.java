/**
 * ===============================================================
 * File name : PersonEventListener.java
 * Created by injeahwang on 2021-08-01
 * ===============================================================
 */
package com.example.streamfunctiondemo.listener;

import com.example.streamfunctiondemo.domainevent.PersonPublishedEvent;
import com.example.streamfunctiondemo.repository.Person;
import com.example.streamfunctiondemo.repository.PersonRepository;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("DISABLED")
public class PersonEventListener implements ApplicationListener<PersonPublishedEvent> {

    private final PersonRepository personRepository;

    public PersonEventListener(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(PersonPublishedEvent personPublishedEvent) {
        System.out.println("==========Event Consume==========");
        Person eventPerson = personPublishedEvent.getPerson();

        System.out.println("person name : " + eventPerson.getName());

        this.personRepository.save(eventPerson);

        System.out.println("======================================");
    }
}
