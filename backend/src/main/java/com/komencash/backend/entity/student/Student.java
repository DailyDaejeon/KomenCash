package com.komencash.backend.entity.student;

import com.komencash.backend.entity.job.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
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

    public Student(String nickname, String password, Job job) {
        this.nickname = nickname;
        this.password = password;
        this.job = job;
    }

    public void updatePw(){
        this.password ="1234";
    }

    public void updateJob(Job job){
        this.job = job;
    }

}
