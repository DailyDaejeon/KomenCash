package com.komencash.backend.entity.vote;

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
@Table(name = "`vote_attend`")
public class VoteAttend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "choice_item_num")
    private int choiceItemNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;


    public VoteAttend(int choiceItemNum, Vote vote, Student student){
        this.choiceItemNum = choiceItemNum;
        this.vote = vote;
        this.student = student;
    }
}
