package com.komencash.backend.entity.board;

import com.komencash.backend.dto.board.BoardAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void updateBoard(BoardAddUpdateRequestDto boardAddUpdateRequestDto) {
        this.title = boardAddUpdateRequestDto.getTitle();
        this.content = boardAddUpdateRequestDto.getContent();
    }


    public Board(BoardAddUpdateRequestDto boardAddUpdateRequestDto, Group group){
        this.id = boardAddUpdateRequestDto.getId();
        this.title = boardAddUpdateRequestDto.getTitle();
        this.content = boardAddUpdateRequestDto.getContent();
        this.group = group;
    }

}
