package com.example.streamfunctiondemo.configuration;

import com.example.streamfunctiondemo.repository.Person;
import com.example.streamfunctiondemo.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class KafkaDemoConfiguration {

    private final Sinks.Many<String> emitter = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, true);

    private final PersonRepository repository;

    public KafkaDemoConfiguration(PersonRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Supplier<Flux<String>> publishTest() {
        return emitter::asFlux;
    }


    @Bean Consumer<List<Person>> consumeTest(){
        return this::batchInsert;
    }

    private void batchInsert(List<Person> messages){
        System.out.println("Insert to DB ===> list size : "+ messages.size());
        long start = System.currentTimeMillis();
        String res = this.repository.saveAll(messages).toString();
        System.out.println("[Batch Insert Time]===> " + (System.currentTimeMillis()-start)/1000.0);
    }

}

