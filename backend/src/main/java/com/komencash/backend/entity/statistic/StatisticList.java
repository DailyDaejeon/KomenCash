package com.komencash.backend.entity.statistic;

import com.komencash.backend.dto.statistic.StatisticListAddRequestDto;
import com.komencash.backend.entity.group.Group;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`statistic_list`")
public class StatisticList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "submit_content")
    private String submitContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_send")
    private boolean isSend;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    public StatisticList(StatisticListAddRequestDto statisticListAddRequestDto, Group group){
        this.submitContent = statisticListAddRequestDto.getSumbmitContent();
        this.startDate = statisticListAddRequestDto.getStartDate();
        this.endDate = statisticListAddRequestDto.getEndDate();
        this.isSend = false;
        this.group = group;
    }
}
