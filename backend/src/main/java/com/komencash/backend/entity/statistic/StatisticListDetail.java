package com.komencash.backend.entity.statistic;

import com.komencash.backend.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "statistic_list_detail")
public class StatisticListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "submit_content")
    private String submitContent;

    @Column(name = "is_submission")
    private boolean isSubmission;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "statistic_list_id")
    private StatisticList statisticList;
}
