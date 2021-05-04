package com.komencash.backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInsertUpdateRequest {

    private int id;
    private String title;
    private String content;
    private int groupId;
}
