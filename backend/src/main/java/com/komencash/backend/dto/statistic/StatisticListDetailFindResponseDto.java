package com.komencash.backend.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticListDetailFindResponseDto {

    private int id;
    private boolean isSubmission;
    private int studentId;
    private String studentNickname;
}
