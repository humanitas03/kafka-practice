/**
 * ===============================================================
 * File name : KafkaDeomController.java
 * Created by injeahwang on 2021-04-02
 * ===============================================================
 */
package com.example.kafkademo.kafkademo

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.json.GsonMapper
import org.springframework.cloud.function.json.JacksonMapper
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.integration.support.json.JacksonJsonUtils
import org.springframework.messaging.support.GenericMessage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier

@RestController
@Configuration
class KafkaDemoController {
    @Autowired
    private val streamBridge: StreamBridge? = null

    //    @Qualifier("publishTest")
    private val emitter = Sinks.many().multicast().onBackpressureBuffer<SendObj>(Queues.SMALL_BUFFER_SIZE, true)

    //    @Autowired
    //    @Qualifier("publishTest")
    //    private Supplier<Flux<String>> publishTest;
    private val myDestination = "mytopic"

    /**
     * 10000개의 랜덤한 String을 생성하여 Topic에 publish한다
     * 1000개의요청이 100ms 대기
     */
    @GetMapping("/v1/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun produceTest() {

        val start = System.currentTimeMillis()
        for( i in 0 until 10){
            runBlocking {
                stringListGenerator(1000)
                        .forEach(Consumer { t: SendObj -> emitter.tryEmitNext(t) })
                delay(100L)
            }
        }

        println("api lead time : " + (System.currentTimeMillis() - start))
    }

    private fun stringListGenerator(maxCnt: Int): List<SendObj> {
        val start = System.currentTimeMillis()
        val res: MutableList<SendObj> = ArrayList()
        for (i in 0 until maxCnt) {
            val newStr = RandomStringUtils.randomAlphanumeric(17).toUpperCase()
            res.add(SendObj(value=newStr))
        }
        println("generator lead time : " + (System.currentTimeMillis() - start))
        return res
    }

    @Bean
    fun consumeTest(): Supplier<Flux<SendObj>> {
        return Supplier { emitter.asFlux() }
    }
}

data class SendObj(
//        val id: Long?,
        val value: String
)