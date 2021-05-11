package com.komencash.backend.entity.statistic;

import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statistic_list_detail")
public class StatisticListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_submission")
    private boolean isSubmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statistic_list_id")
    private StatisticList statisticList;

    public StatisticListDetail(Student student, StatisticList statisticList){
        this.isSubmission = false;
        this.statisticList = statisticList;
        this.student = student;
    }
}
