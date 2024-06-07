package org.example.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.domain.enums.BoardCategory;

@Data
@AllArgsConstructor
public class BoardSearchRequest {
    private String sortType;
    private String keyword;
    private String category;
}
