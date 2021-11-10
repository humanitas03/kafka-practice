package com.example.streamfunctiondemo.configuration;

import com.example.streamfunctiondemo.repository.BatchInsertRepository;
import com.example.streamfunctiondemo.repository.Person;
import com.example.streamfunctiondemo.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class KafkaDemoConfiguration {

    private final Sinks.Many<String> emitter = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, true);


    private final BatchInsertRepository batchInsertRepository;
    private final PersonRepository personRepository;

    public KafkaDemoConfiguration(BatchInsertRepository batchInsertRepository,PersonRepository personRepository) {
        this.batchInsertRepository = batchInsertRepository;
        this.personRepository = personRepository;
    }

    @Bean
    public Supplier<Flux<String>> publishTest() {
        return emitter::asFlux;
    }

    @Bean public Consumer<List<Person>> consumeTest() {
            ObjectMapper objectMapper = new ObjectMapper();

        return it->{
            try{
                it.stream().forEach(person-> {
                    try {
                        System.out.println("===> Recieved Message : "+ objectMapper.writeValueAsString(person));
                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                });

                this.batchInsertRepository.batchInsertByJdbcTemplate(it);
//                this.personRepository.saveAll(it);
            }catch(Exception e){
                System.out.println("======Error=====");
                System.out.println(e.getMessage());
            }
        };
    }


}

