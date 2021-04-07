package com.example.streamfunctiondemo.controller;

import com.example.streamfunctiondemo.repository.Person;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

@RestController
public class KafkaDemoController {

    @Autowired
    private StreamBridge streamBridge;

    private final String myDestination = "mytopic";

    private static String[] genderFlag = {"M","F"};
    /**
     * 10000개의 랜덤한 String을 생성하여 Topic에 publish한다
     */
    @GetMapping("/test/{maxCount}")
    public void produceTest(@PathVariable int maxCount){
        long start = System.currentTimeMillis();
        this.personGenerator(maxCount)
                .stream().parallel()
                .forEach(it->{
//                    System.out.println("Thread : "+Thread.currentThread().getName()+", value : " + it.getName());
                    this.streamBridge.send(myDestination,it);
                });
        System.out.println("api exec time : "+(System.currentTimeMillis()-start));
    }

    private List<Person> personGenerator(int maxCnt){
        long start =System.currentTimeMillis();
        List<Person> res = new ArrayList<>();

        Random rand = new Random();
        for(int i=0; i<maxCnt; i++){
            //name
            String randName = RandomStringUtils.randomAlphanumeric(7).toUpperCase();

            //gender
            String randGender = genderFlag[(rand.nextInt(2))];

            //phoneNumber
            String phoneNumber = "010".concat(RandomStringUtils.random(8,33,125,false,true));


            res.add(Person.of(randName,randGender,rand.nextInt(100),phoneNumber));
        }
        System.out.println("generator exec time : " + (System.currentTimeMillis()-start));
        return res;
    }

}
