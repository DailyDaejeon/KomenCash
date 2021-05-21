package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.request.JobAddReqAddRequestDto;
import com.komencash.backend.entity.student.Student;
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
@Table(name = "job_add_request_history")
public class JobAddRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public JobAddRequestHistory(JobAddReqAddRequestDto jobAddReqAddRequestDto, Student student){
        this.name = jobAddReqAddRequestDto.getJobName();
        this.content = jobAddReqAddRequestDto.getContent();
        this.accept = Accept.before_confirm;
        this.student = student;
    }

    public void updateJobAddRequestAccept(Accept accept){
        this.accept = accept;
    }
}
