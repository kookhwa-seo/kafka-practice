package com.practice.kafka;

import com.github.javafaker.Faker;
import com.practice.kafka.component.KafkaConsumer;
import com.practice.kafka.component.KafkaProducer;
import com.practice.kafka.dto.PracticeDTO;
import com.practice.kafka.repository.PracticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092"},
        ports = { 9092 }
)
@Slf4j
class KafkaConsumerTest {
    final private String TOPIC = "practice";

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private PracticeRepository practiceRepository;

    private Faker faker = new Faker();

    private StopWatch stopWatch = new StopWatch();

    @Test
    public void giveEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {

        PracticeDTO payload = PracticeDTO.builder()
                                         .name(faker.name().fullName())
                                         .id(UUID.randomUUID())
                                         .viewCount(20)
                                         .build();
        PracticeDTO payload2 = PracticeDTO.builder()
                                          .name(faker.name().fullName())
                                          .id(UUID.randomUUID())
                                          .viewCount(50)
                                          .build();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                producer.send(TOPIC, payload);
            } else {
                producer.send(TOPIC, payload2);
            }
        }

        // 모든 메시지를 수신할 때까지 기다리기
        consumer.getLatch().await(10, TimeUnit.SECONDS);
        stopWatch.stop();
        log.info("time : " + stopWatch.getTotalTimeSeconds() + " seconds.");
        log.info("############# kafka test #############");
        //Thread.sleep(1000*180);
        int size = practiceRepository.findAll().size();
        log.info(">>> size : " + size);
    }
}