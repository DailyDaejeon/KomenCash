package com.komencash.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.komencash.backend.dto.vote.VoteResultResponse;
import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import com.komencash.backend.entity.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAddReqSelectResponse {

    private int id;
    private String title;
    private String content;
    private Accept accpet;
    private int student_id;
    private String student_nickname;
    private VoteResultResponse voteResultResponse;

    public LawAddReqSelectResponse(LawAddRequestHistory lawAddRequestHistory, VoteResultResponse voteResultResponse) {
        this.id = lawAddRequestHistory.getId();
        this.title = lawAddRequestHistory.getTitle();
        this.content = lawAddRequestHistory.getContent();
        this.accpet = lawAddRequestHistory.getAccpet();
        this.student_id = lawAddRequestHistory.getStudent().getId();
        this.student_nickname = lawAddRequestHistory.getStudent().getNickname();
        this.voteResultResponse = voteResultResponse;
    }
}
