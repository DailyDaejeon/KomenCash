package com.komencash.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAddReqAddRequestDto {

    private String jobName;
    private String content;
    private int studentId;

}
