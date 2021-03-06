package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeFindDetailResponseDto {

    private int id;
    private String title;
    private String content;
    private Accept accept;
    private int studentId;
    private String studentNickname;
    private int jobId;
    private String jobName;

    public ResumeFindDetailResponseDto(ResumeRequestHistory resumeRequestHistory){
        this.id = resumeRequestHistory.getId();
        this.title = resumeRequestHistory.getTitle();
        this.content = resumeRequestHistory.getContent();
        this.accept = resumeRequestHistory.getAccept();
        this.studentId = resumeRequestHistory.getStudent().getId();
        this.studentNickname = resumeRequestHistory.getStudent().getNickname();
        this.jobId = resumeRequestHistory.getJob().getId();
        this.jobName = resumeRequestHistory.getJob().getName();
    }
}
