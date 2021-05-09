package com.komencash.backend.dto.vote;

import com.komencash.backend.entity.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteFindResponseDto {

    private int id;
    private String title;
    private String content;
    private int studentId;
    private String studentNickname;
    private List<VoteItemFindResponseDto> voteItemResultResponses;

    public VoteFindResponseDto(Vote vote, List<VoteItemFindResponseDto> voteItemResultResponses) {
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.content = vote.getContent();
        this.studentId = vote.getStudent().getId();
        this.studentNickname = vote.getStudent().getNickname();
        this.voteItemResultResponses = voteItemResultResponses;
    }

}
