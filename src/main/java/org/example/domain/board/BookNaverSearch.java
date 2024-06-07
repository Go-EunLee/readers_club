package org.example.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BookNaverSearch {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookNaverResult> items;
}
