package com.komencash.backend.dto.board;

import com.komencash.backend.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSelectResponse {

    private int id;
    private String title;
    private String content;

    public BoardSelectResponse(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
