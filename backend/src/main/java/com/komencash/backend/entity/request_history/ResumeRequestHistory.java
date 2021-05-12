package com.komencash.backend.entity.request_history;

import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.job.Job;
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
@Table(name = "resume_request_history")
public class ResumeRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public ResumeRequestHistory(String title, String content, Accept accept, Job job, Student student) {
        this.title = title;
        this.content = content;
        this.accept = accept;
        this.job = job;
        this.student = student;
    }

    public void updateResumeAccept(Accept accept){
        this.accept = accept;
    }
}
