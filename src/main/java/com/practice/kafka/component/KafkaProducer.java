package com.practice.kafka.component;

import com.practice.kafka.dto.PracticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PracticeDTO> kafkaTemplate;

    public void send(String topic, PracticeDTO payload) {
        log.info("sending payload={} to topic={}", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
