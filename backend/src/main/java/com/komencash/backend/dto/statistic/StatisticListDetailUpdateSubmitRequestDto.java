package com.komencash.backend.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticListDetailUpdateSubmitRequestDto {

    private int statisticListId;
    private int studentId;

}
