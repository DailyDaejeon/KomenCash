package com.komencash.backend.entity.vote;

import com.komencash.backend.dto.vote.VoteInsertUpdateRequest;
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

    public Vote(VoteInsertUpdateRequest voteInsertUpdateRequest, Student student) {
        this.title = voteInsertUpdateRequest.getTitle();
        this.content = voteInsertUpdateRequest.getContent();
        this.student = student;
    }

}
