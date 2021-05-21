package com.komencash.backend.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticListAddRequestDto {

    private String sumbmitContent;
    private Date startDate;
    private Date endDate;
    private int groupId;
}
