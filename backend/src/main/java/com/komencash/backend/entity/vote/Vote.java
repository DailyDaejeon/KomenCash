package com.komencash.backend.entity.vote;

import com.komencash.backend.dto.vote.VoteAddUpdateRequestDto;
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
@Table(name = "`vote`")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Vote(VoteAddUpdateRequestDto voteAddUpdateRequestDto, Student student) {
        this.title = voteAddUpdateRequestDto.getTitle();
        this.content = voteAddUpdateRequestDto.getContent();
        this.student = student;
    }

}
