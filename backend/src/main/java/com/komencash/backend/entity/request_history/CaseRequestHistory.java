package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.request.CaseAddRequestDto;
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
@Table(name = "case_request_history")
public class CaseRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "fine")
    private int fine;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "police_id")
    private Student police;

    public void updateCaseAccept(Accept accept) {
        this.accept = accept;
    }

    public CaseRequestHistory(CaseAddRequestDto caseAddRequestDto, Student student, Student police){
        this.content = caseAddRequestDto.getContent();
        this.fine = caseAddRequestDto.getFine();
        this.accept = Accept.before_confirm;
        this.student = student;
        this.police = police;
    }
}
