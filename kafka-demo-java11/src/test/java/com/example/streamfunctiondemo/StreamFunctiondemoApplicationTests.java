package com.example.streamfunctiondemo;

import com.example.streamfunctiondemo.repository.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest(classes = StreamFunctiondemoApplication.class)
@Import({TestChannelBinderConfiguration.class})
class StreamFunctiondemoApplicationTests {

    @Autowired
    private InputDestination input;

    @Test
    void contextLoads() {
        List<Person> testList = new ArrayList<>();
        testList.add(Person.of("test","M",10,"00000000000"));
        assertDoesNotThrow(()->input.send(new GenericMessage<List<Person>>(testList)));
    }

}
