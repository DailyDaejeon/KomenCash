package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemResultResponse implements VoteItemResultInterface{

    private int id;
    private int itemNum;
    private String content;
    private int resultCnt;

    public VoteItemResultResponse(VoteItemResultInterface voteItemResultInterface) {
        this.id = voteItemResultInterface.getId();
        this.itemNum = voteItemResultInterface.getItemNum();
        this.content = voteItemResultInterface.getContent();
        this.resultCnt = voteItemResultInterface.getResultCnt();
    }
}
