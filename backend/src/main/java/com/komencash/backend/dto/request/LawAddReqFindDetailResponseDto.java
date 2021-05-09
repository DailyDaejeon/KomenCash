package com.komencash.backend.dto.request;

import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAddReqFindDetailResponseDto {

    private int id;
    private String title;
    private String content;
    private Accept accpet;
    private int student_id;
    private String student_nickname;
    private VoteFindResponseDto voteFindResponseDto;

    public LawAddReqFindDetailResponseDto(LawAddRequestHistory lawAddRequestHistory, VoteFindResponseDto voteFindResponseDto) {
        this.id = lawAddRequestHistory.getId();
        this.title = lawAddRequestHistory.getTitle();
        this.content = lawAddRequestHistory.getContent();
        this.accpet = lawAddRequestHistory.getAccpet();
        this.student_id = lawAddRequestHistory.getStudent().getId();
        this.student_nickname = lawAddRequestHistory.getStudent().getNickname();
        this.voteFindResponseDto = voteFindResponseDto;
    }
}
