package com.komencash.backend.entity;

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

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;
//
//    @OneToOne
//    @JoinColumn(name ="job_id")
//    private Job job;

}