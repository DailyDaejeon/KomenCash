package com.komencash.backend.dto.law;

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
    private Vote vote;
    private Student student;

    public LawAddReqSelectResponse(LawAddRequestHistory lawAddRequestHistory) {
        this.id = lawAddRequestHistory.getId();
        this.title = lawAddRequestHistory.getTitle();
        this.content = lawAddRequestHistory.getContent();
        this.accpet = lawAddRequestHistory.getAccpet();
        this.vote = lawAddRequestHistory.getVote();
        this.student = lawAddRequestHistory.getStudent();
    }
}
