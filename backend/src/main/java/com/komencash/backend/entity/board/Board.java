package com.komencash.backend.entity.board;

import com.komencash.backend.dto.board.BoardInsertUpdateRequest;
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


    public Board(BoardInsertUpdateRequest boardInsertUpdateRequest, Group group){
        this.id = boardInsertUpdateRequest.getId();
        this.title = boardInsertUpdateRequest.getTitle();
        this.content = boardInsertUpdateRequest.getContent();
        this.group = group;
    }
}
