package com.komencash.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseAddRequestDto {

    private String content;
    private int fine;
    private int studentId;
    private int policeId;
}
