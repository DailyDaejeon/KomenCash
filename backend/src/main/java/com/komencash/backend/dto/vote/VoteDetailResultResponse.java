package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDetailResultResponse {

    private int id;
    private String title;
    private String content;
    private int studentId;
    private String studentNickname;
    private List<VoteItemResultResponse> voteItemResultResponses;
    private List<VoteAttendResponse> voteAttendResponses;


    public VoteDetailResultResponse(VoteResultResponse voteResultResponse, List<VoteAttendResponse> voteAttendResponses) {
        this.id = voteResultResponse.getId();
        this.title = voteResultResponse.getTitle();
        this.content = voteResultResponse.getContent();
        this.studentId = voteResultResponse.getStudentId();
        this.studentNickname = voteResultResponse.getStudentNickname();
        this.voteItemResultResponses = voteResultResponse.getVoteItemResultResponses();
        this.voteAttendResponses = voteAttendResponses;
    }
}
