package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.law.LawAddReqAddRequestDto;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.vote.Vote;
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
@Table(name = "law_add_request_history")
public class LawAddRequestHistory {

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
    private Accept accpet;

    @OneToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public LawAddRequestHistory(LawAddReqAddRequestDto lawAddReqAddRequestDto, Vote vote, Student student){
        this.title = lawAddReqAddRequestDto.getTitle();
        this.content = lawAddReqAddRequestDto.getContent();
        this.accpet = Accept.before_confirm;
        this.vote = vote;
        this.student = student;
    }

    public void updateLawRequestAccept(Accept accept){
        this.accpet = accept;
    }
}
