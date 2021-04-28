package com.komencash.backend.dto.vote;

import com.komencash.backend.entity.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultResponse {

    private int id;
    private String title;
    private String content;
    private int studentId;
    private String studentNickname;
    private List<VoteItemResultResponse> voteItemResultResponses;

    public VoteResultResponse(Vote vote, List<VoteItemResultResponse> voteItemResultResponses) {
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.content = vote.getContent();
        this.studentId = vote.getStudent().getId();
        this.studentNickname = vote.getStudent().getNickname();
        this.voteItemResultResponses = voteItemResultResponses;
    }

}
