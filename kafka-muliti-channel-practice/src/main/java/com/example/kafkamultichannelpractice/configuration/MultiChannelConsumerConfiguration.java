/**
 * ===============================================================
 * File name : MultiChannelConsumerConfiguration.java
 * Created by injeahwang on 2021-04-09
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.configuration;

import com.example.kafkamultichannelpractice.entity.Person;
import com.example.kafkamultichannelpractice.entity.PersonHistory;
import com.example.kafkamultichannelpractice.exception.PersonErrorResponse;
import com.example.kafkamultichannelpractice.exception.PersonProcessException;
import com.example.kafkamultichannelpractice.repository.PersonHistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


@Configuration
public class MultiChannelConsumerConfiguration {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    PersonHistoryRepository personRepository;

    @Bean
    public Function<Tuple3<Flux<List<Person>>, Flux<List<Person>>, Flux<List<Person>>>, Flux<List<Person>>> multiChannelConsumer() {
        return tuple ->{
            Flux<List<Person>> mergedFlux = Flux.merge(tuple.getT1(), tuple.getT2(), tuple.getT3());
            return mergedFlux
                    .doOnNext(this::processFlux)
                    /** Flux<Collection<T>> 구조이기 때문에...
                     * doOnNext시 Exception이 전파가 되면.. List전체가 처리가 안되는 문제 발생.
                     */
                    .onErrorContinue( (throwable,o) -> {
                            System.out.println("==========[ON OUTER METHOD]=====================");

                            /** Flux안에 래핑된 List 전체에 대한 처리가 필요하다.*/
//                            System.out.println(throwable.getLocalizedMessage());
//                            List<Person> pList = (List<Person>)o;
//                            //
//                            pList.forEach( it->{
//                                PersonHistory hist = new PersonHistory();
//                                BeanUtils.copyProperties(it,hist);
//                                hist.setErrorCause("["+throwable.getClass().getSimpleName()+"] | " +throwable.getLocalizedMessage()+"|"+throwable.getCause());
//                                this.personRepository.save(hist);
//                            });
                            System.out.println("=====================================");
                    })
                    ;

        };
    }

    /** Flux로 래핑된 List<Person>을 처리
     * 
     * @param persons
     */
    private void processFlux(List<Person> persons){
            persons.forEach(it -> {
                try{
                    this.webClientBuilder
                            .build()
                            .post()
                            .uri("http://localhost:12000/post")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .bodyValue(it)
                            .retrieve()
                            .onStatus(HttpStatus::isError,
                                    //클라이언트에서는 이름이 "WJ"가 포함된 사람의 경우 에러를 리턴한다.
                                    response -> response.bodyToMono(PersonErrorResponse.class).map(error->
                                            new PersonProcessException(error.getErrorMessage(),error.getErrorCode()))
                            )
                            .bodyToMono(Person.class)
                            .block();
                } catch(Exception e){
                    System.out.println("---------------------------");
                    System.out.println(" : " + it.toString());
                    /** 건건히 Webclient에서 실패한 데이터만 DB 처리하고자 할때 이부분을 활성화 환다.*/
                    PersonHistory hist = new PersonHistory();
                    BeanUtils.copyProperties(it,hist);

                    if(e instanceof PersonProcessException) {
                        hist.setErrorCause("["+e.getClass().getSimpleName()+"] | [Error Code] : " +((PersonProcessException) e).getErrorCode()+"| [ErrorMesage] : "+e.getMessage());

                    }else{
                        hist.setErrorCause("["+e.getClass().getSimpleName()+"] | " +e.getLocalizedMessage()+"|"+e.getCause());
                    }

                    this.personRepository.save(hist);
                    System.out.println("---------------------------");
                    /** Exception을 Throw하게되면, onErrorContinue에서 에러를 핸들링 하게 된다.*/
//                    throw e;
                }

                    }

            );
    }
}
