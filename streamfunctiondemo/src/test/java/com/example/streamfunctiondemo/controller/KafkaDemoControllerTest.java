package com.example.streamfunctiondemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureWebTestClient
public class KafkaDemoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Transactional
    public void kafkaDemoApiTest() throws Exception{
        webTestClient.get().uri("/test/10000")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

}
