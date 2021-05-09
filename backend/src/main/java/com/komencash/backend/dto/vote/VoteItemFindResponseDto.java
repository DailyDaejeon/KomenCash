package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemFindResponseDto implements VoteItemFindInterface {

    private int id;
    private int itemNum;
    private String content;
    private int resultCnt;

    public VoteItemFindResponseDto(VoteItemFindInterface voteItemFindInterface) {
        this.id = voteItemFindInterface.getId();
        this.itemNum = voteItemFindInterface.getItemNum();
        this.content = voteItemFindInterface.getContent();
        this.resultCnt = voteItemFindInterface.getResultCnt();
    }
}
