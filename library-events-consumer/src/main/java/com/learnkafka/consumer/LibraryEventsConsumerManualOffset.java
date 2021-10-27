package com.learnkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
// Not using manual offset rather batch commit which is default
//@Component
@Slf4j
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener {

    @Override
    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
        log.info("Consumer Record : {} ", consumerRecord);
        acknowledgment.acknowledge();
    }

    @Override
    public void onMessage(Object object) {

    }


}
