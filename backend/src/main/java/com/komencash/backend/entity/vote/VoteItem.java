package com.komencash.backend.entity.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`vote_item`")
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "item_num")
    private int itemNum;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
}
