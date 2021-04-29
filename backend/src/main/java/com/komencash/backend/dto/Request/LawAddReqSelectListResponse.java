package com.komencash.backend.dto.Request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAddReqSelectListResponse {

    private int id;
    private String title;
    private Accept accept;
    private int student_id;
    private String student_nickname;

    public LawAddReqSelectListResponse(LawAddRequestHistory lawAddRequestHistory) {
        this.id = lawAddRequestHistory.getId();
        this.title = lawAddRequestHistory.getTitle();
        this.accept = lawAddRequestHistory.getAccpet();
        this.student_id = lawAddRequestHistory.getStudent().getId();
        this.student_nickname = lawAddRequestHistory.getStudent().getNickname();
    }
}
