package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.CaseRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseSelectResponse {

    private int id;
    private int fine;
    private String content;
    private Accept accept;
    private int studentId;
    private String studentNickname;
    private int policeId;
    private String policeNickname;

    public CaseSelectResponse(CaseRequestHistory caseRequestHistory) {
        this.id = caseRequestHistory.getId();
        this.fine = caseRequestHistory.getFine();
        this.content = caseRequestHistory.getContent();
        this.accept = caseRequestHistory.getAccept();
        this.studentId = caseRequestHistory.getStudent().getId();
        this.studentNickname = caseRequestHistory.getStudent().getNickname();
        this.policeId = caseRequestHistory.getPolice().getId();
        this.policeNickname = caseRequestHistory.getPolice().getNickname();
    }
}
