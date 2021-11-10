/**
 * ===============================================================
 * File name : TestController.java
 * Created by injeahwang on 2021-04-11
 * ===============================================================
 */
package com.example.kafkamultichannelpracticeresttester;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class TestController {

    final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private StreamBridge streamBridge;

    private final String myDestination = "mytopic";

    private static String[] genderFlag = {"M","F"};

    @GetMapping("/test/{maxCount}")
    public void produceTest(@PathVariable int maxCount) {
        long start = System.currentTimeMillis();
        this.personGenerator(maxCount)
                .parallelStream()
//                .stream().parallel()
                .forEach(it->{
                    System.out.println("Thread : "+Thread.currentThread().getName()+", value Name : " + it.getName());
                    this.streamBridge.send(myDestination,it);
                });
        System.out.println("api exec time : "+(System.currentTimeMillis()-start));
    }

    @PostMapping("/post")
    public Person getMultiChannelMessage(@RequestBody Person person) throws Exception{

        //요청으로 받은 Person의 Username에 "WJ"문자열이 잇는 경우 Error 리턴
        if(person.getUserName().contains("WJ"))
            throw new Exception("The username contains exception String('WJ') [ " +person.getUserName()+" ]" );

        System.out.println("=====SUccess RESPONSE : "+ person.toString());
        return person;
    }

    @GetMapping("/test")
    public Person getTest(@RequestBody Person person) {
        System.out.println("=====SUccess RESPONSE : "+ person.toString());
        return person;
    }

    @GetMapping("/resttest/{maxCount}")
    public void restTest(@PathVariable int maxCount) {
        long start = System.currentTimeMillis();

        this.personGenerator(maxCount)
//                .parallelStream()
                .stream().parallel()
                .forEach(it->{
                    System.out.println("Thread : "+Thread.currentThread().getName()+", value Name : " + it.getName());
                    restTemplate.postForEntity("http://localhost:8080/resttest", it, SendPerson.class);
                });
        System.out.println("api exec time : "+(System.currentTimeMillis()-start));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e) {
        System.err.println(e.getClass());
        return new ErrorResponse(e.getMessage(),"5002");

    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class ErrorResponse {
        private String errorMessage;
        private String errorCode;
    }

    private List<SendPerson> personGenerator(int maxCnt){
        long start =System.currentTimeMillis();
        List<SendPerson> res = new ArrayList<>();

        Random rand = new Random();
        for(int i=0; i<maxCnt; i++){
            //name
            String randName = RandomStringUtils.randomAlphanumeric(7).toUpperCase();

            //gender
            String randGender = genderFlag[(rand.nextInt(2))];

            //phoneNumber
            String phoneNumber = "010".concat(RandomStringUtils.random(8,33,125,false,true));

            // Message
            String message = "[Web 발신] " + randName + " 고객님 결제 금액 : " + (rand.nextInt(50000)*100)
                    + " \n  승인일시 : " + LocalDateTime.now() + " . ";

            res.add(SendPerson.of(randName,randGender,rand.nextInt(100),phoneNumber,message));
        }
        System.out.println("generator exec time : " + (System.currentTimeMillis()-start));
        return res;
    }

}
