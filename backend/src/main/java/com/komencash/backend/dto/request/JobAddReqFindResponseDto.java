package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.JobAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAddReqFindResponseDto {

    private int id;
    private String name;
    private String content;
    private Accept accept;
    private int studentId;
    private String studentNickname;

    public JobAddReqFindResponseDto(JobAddRequestHistory jobAddRequestHistory) {
        this.id = jobAddRequestHistory.getId();
        this.name = jobAddRequestHistory.getName();
        this.content = jobAddRequestHistory.getContent();
        this.accept = jobAddRequestHistory.getAccept();
        this.studentId = jobAddRequestHistory.getStudent().getId();
        this.studentNickname = jobAddRequestHistory.getStudent().getNickname();
    }
}
