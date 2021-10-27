package com.learnkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.entity.LibraryEvent;
import com.learnkafka.repository.LibraryEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryEventService {

    private final LibraryEventRepository repository;

    public void processLibraryEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        LibraryEvent libraryEvent = new ObjectMapper().readValue(consumerRecord.value(), LibraryEvent.class);
        log.info("libraryEvent : {}", libraryEvent);
        switch (libraryEvent.getLibraryEventType()) {
            case ADD:
                save(libraryEvent);
            case UPDATE:
                // TODO: 27-10-2021
            default:
                log.info("Invalid libraryEventType");
        }
    }

    private void save(LibraryEvent libraryEvent) {
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        repository.save(libraryEvent);
        log.info("Successfully persisted to DB : {}", libraryEvent);
    }

}
