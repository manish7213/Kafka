package com.learnkafka.integration.controller;

import com.learnkafka.domain.Book;
import com.learnkafka.domain.LibraryEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryEventControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void postLibraryEvent() {

        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Josh")
                .bookName("Reactive Spring")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());

        HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

        ResponseEntity<LibraryEvent> responseEntity = restTemplate.exchange("/v1/libraryevent", HttpMethod.POST, request, LibraryEvent.class);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
