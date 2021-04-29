package com.komencash.backend.entity.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name ="password")
    private String password;

    @ManyToOne
    @JoinColumn(name ="job_id")
    private Job job;

    public void updatePw(){
        this.password ="1234";
    }

    public void updateJob(Job job){
        this.job = job;
    }

}
