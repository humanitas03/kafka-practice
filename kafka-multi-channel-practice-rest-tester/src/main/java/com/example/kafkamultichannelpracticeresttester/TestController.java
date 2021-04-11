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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping("/post")
    public Person getMultiChannelMessage(@RequestBody Person person) throws Exception{

        //요청으로 받은 Person의 Username에 "WJ"문자열이 잇는 경우 Error 리턴
        if(person.getUserName().contains("WJ"))
            throw new Exception("The username contains exception String('WJ') [ " +person.getUserName()+" ]" );

        System.out.println("=====SUccess RESPONSE : "+ person.toString());
        return person;
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

}
