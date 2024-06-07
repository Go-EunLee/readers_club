package org.example.domain.board;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookNaverResult {
    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
}
