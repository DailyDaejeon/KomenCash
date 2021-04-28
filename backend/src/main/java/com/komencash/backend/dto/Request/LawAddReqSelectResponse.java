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
    private int student_id;
    private String student_nickname;
    private Vote vote;

    public LawAddReqSelectResponse(LawAddRequestHistory lawAddRequestHistory) {
        this.id = lawAddRequestHistory.getId();
        this.title = lawAddRequestHistory.getTitle();
        this.content = lawAddRequestHistory.getContent();
        this.accpet = lawAddRequestHistory.getAccpet();
        this.student_id = lawAddRequestHistory.getStudent().getId();
        this.student_nickname = lawAddRequestHistory.getStudent().getNickname();
        this.vote = lawAddRequestHistory.getVote();
    }
}
