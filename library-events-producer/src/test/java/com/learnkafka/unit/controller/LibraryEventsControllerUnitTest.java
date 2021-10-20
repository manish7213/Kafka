package com.learnkafka.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.controller.LibraryEventsController;
import com.learnkafka.domain.Book;
import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventsControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryEventProducer libraryEventProducer;

    @Test
    void postLibraryEvents() throws Exception {

        //given
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Josh")
                .bookName("Reactive Spring")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        //when
        String jsonString = new ObjectMapper().writeValueAsString(libraryEvent);
        doNothing().when(libraryEventProducer).sendLibraryEvent(libraryEvent);
        mockMvc.perform(post("/v1/libraryevent")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //then
    }

    @Test
    void postLibraryEvents_4xx() throws Exception {

        //given
        Book book = Book.builder()
                .bookId(null)
                .bookAuthor(null)
                .bookName("Reactive Spring")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        //when
        String jsonString = new ObjectMapper().writeValueAsString(libraryEvent);
        String expectedErrorMessage = "book.bookAuthor - must not be blank,book.bookId - must not be null";
        doNothing().when(libraryEventProducer).sendLibraryEvent(libraryEvent);
        mockMvc.perform(post("/v1/libraryevent")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                //To view exact error message : we will take help of controller advice
                .andExpect(content().string(expectedErrorMessage));


    }
}
