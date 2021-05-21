package com.komencash.backend.dto.statistic;

import com.komencash.backend.entity.statistic.StatisticList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticListFindDetailResponseDto {

    private int id;
    private Date startDate;
    private Date endDate;
    private String submitContent;
    private List<StatisticListDetailFindResponseDto> statisticListDetailFindResponseDtos;

    public StatisticListFindDetailResponseDto(StatisticList statisticList,
                                              List<StatisticListDetailFindResponseDto> statisticListDetailFindResponseDtos){
        this.id = statisticList.getId();
        this.startDate = statisticList.getStartDate();
        this.endDate = statisticList.getEndDate();
        this.submitContent = statisticList.getSubmitContent();
        this.statisticListDetailFindResponseDtos = statisticListDetailFindResponseDtos;
    }
}
