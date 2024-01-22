package com.practice.kafka.component;

import com.practice.kafka.dto.PracticeDTO;
import com.practice.kafka.entity.Practice;
import com.practice.kafka.repository.PracticeRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class KafkaConsumer {
    final private PracticeRepository practiceRepository;
    private CountDownLatch latch = new CountDownLatch(10);
    private List<PracticeDTO> payloads = new ArrayList<>();
    private PracticeDTO payload;

    @KafkaListener(topics = "practice",
            containerFactory = "filterListenerContainerFactory")
    public void receive(ConsumerRecord<String, PracticeDTO> consumerRecord) {
        payload = consumerRecord.value();
        //log.info("received payload = {}", payload.toString());
        Practice practice = Practice.builder()
                .id(payload.getId())
                .name(payload.getName())
                .viewCount(payload.getViewCount())
                .build();
        practiceRepository.save(practice);

        payloads.add(payload);
        latch.countDown();
    }

    public List<PracticeDTO> getPayloads() {
        return payloads;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
