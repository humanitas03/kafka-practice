/**
 * ===============================================================
 * File name : MultiChannelTestController.java
 * Created by injeahwang on 2021-04-09
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.controller;

import com.example.kafkamultichannelpractice.entity.Person;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class MultiChannelTestController {

    private final String topicGname = "multi.topic";
    private final String TOPIC1 = "multi.topic1";
    private final String TOPIC2 = "multi.topic2";
    private final String TOPIC3 = "multi.topic3";
    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/test/{maxCount}")
    public void test(@PathVariable int maxCount){
        this.personGenerator(maxCount).stream().parallel().forEach(
               it-> {
                   it.setTopic(TOPIC1);
                   this.streamBridge.send(TOPIC1,it);
               }
        );

        this.personGenerator(maxCount).stream().parallel().forEach(
                it-> {
                    it.setTopic(TOPIC2);
                    this.streamBridge.send(TOPIC2,it);
                }
        );

        this.personGenerator(maxCount).stream().parallel().forEach(
                it-> {
                    it.setTopic(TOPIC3);
                    this.streamBridge.send(TOPIC3,it);
                }
        );
    }

    @GetMapping("/test/{topicNumber}/{maxCount}")
    public void test(@PathVariable String topicNumber, @PathVariable int maxCount) {
        final String sendTopic = topicGname+topicNumber;

        this.personGenerator(maxCount).stream().parallel().forEach(
                it-> {
                    it.setTopic(sendTopic);
                    this.streamBridge.send(sendTopic,it);
                }
        );
    }



    private List<Person> personGenerator(int maxCnt){
        List<Person> res = new ArrayList<>();

        Random rand = new Random();
        for(int i=0; i<maxCnt; i++){
            //name
            String randName = RandomStringUtils.randomAlphanumeric(7).toUpperCase();

            //gender
            String randGender = "M";

            //phoneNumber
            String phoneNumber = "010".concat(RandomStringUtils.random(8,33,125,false,true));

            Person person = new Person();
            person.setUserName(randName);
            person.setAge(rand.nextInt(100));
            person.setGender(randGender);
            person.setPhoneNumber(phoneNumber);
            person.setSendTime(LocalDateTime.now().toString());

            res.add(person);
        }
        return res;
    }
}
