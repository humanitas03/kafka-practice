package com.example.streamfunctiondemo.configuration;

import com.example.streamfunctiondemo.repository.BatchInsertRepository;
import com.example.streamfunctiondemo.repository.Person;
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
    public KafkaDemoConfiguration(BatchInsertRepository batchInsertRepository) {
        this.batchInsertRepository = batchInsertRepository;
    }

    @Bean
    public Supplier<Flux<String>> publishTest() {
        return emitter::asFlux;
    }

    @Bean public Consumer<List<Person>> consumeTest() {
        return it->{
            try{
                long start = System.currentTimeMillis();
                this.batchInsertRepository.batchInsertByJdbcTemplate(it);
//                System.out.println("==>List size  : "+ it.size());
//                System.out.println("==>Finish Insert : "+ (System.currentTimeMillis()-start) + " ms");
            }catch(Exception e){
                System.out.println("======Error=====");
                System.out.println(e.getMessage());
            }
        };
    }


}

