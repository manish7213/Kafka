package com.learnkafka.domain;

import com.learnkafka.constant.LibraryEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manish
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LibraryEvent {

    private Integer libraryEventId;
    private LibraryEventType libraryEventType;
    private Book book;
}
