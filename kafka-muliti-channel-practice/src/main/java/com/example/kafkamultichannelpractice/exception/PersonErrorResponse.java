/**
 * ===============================================================
 * File name : PersonErrorResponse.java
 * Created by injeahwang on 2021-04-11
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonErrorResponse {
    private String errorMessage;
    private String errorCode;
}
