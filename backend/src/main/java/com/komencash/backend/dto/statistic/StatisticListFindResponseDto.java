package com.komencash.backend.dto.statistic;

import com.komencash.backend.entity.statistic.StatisticList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticListFindResponseDto {

    private int id;
    private Date startDate;
    private Date endDate;
    private String submitContent;

    public StatisticListFindResponseDto(StatisticList statisticList) {
        this.id = statisticList.getId();
        this.startDate = statisticList.getStartDate();
        this.endDate = statisticList.getEndDate();
        this.submitContent = statisticList.getSubmitContent();
    }
}
