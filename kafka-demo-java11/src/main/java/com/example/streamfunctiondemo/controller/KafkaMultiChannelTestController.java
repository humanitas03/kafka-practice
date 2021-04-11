/**
 * ===============================================================
 * File name : KafkaMultiChannelTestController.java
 * Created by injeahwang on 2021-04-10
 * ===============================================================
 */
package com.example.streamfunctiondemo.controller;

import com.example.streamfunctiondemo.repository.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaMultiChannelTestController {

    @PostMapping("/post")
    public MultiChPerson getMultichannelMessage(@RequestBody MultiChPerson person) throws Exception{

        if(person.userName.contains("WJ"))
            throw new Exception("Error!!!!");

        System.out.println("=====SUccess RESPONSE : "+ person.toString());
        return person;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandler(Exception e) {
        System.err.println(e.getClass());
        return new Exception("Error!!");
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class MultiChPerson {
        private String userName;

        private int age;

        private String phoneNumber;

        private String gender;

        private String sendTime;

        private String topic;

        @SneakyThrows
        @Override
        public String toString(){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }
    }
}
