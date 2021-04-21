package com.komencash.backend.entity;

import com.komencash.backend.dto.TeacherInsertRequest;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;


    public Teacher(TeacherInsertRequest teacherInsertRequest) {
        this.id = teacherInsertRequest.getId();
        this.email = teacherInsertRequest.getEmail();
        this.password = teacherInsertRequest.getPassword();
        this.nickname = teacherInsertRequest.getNickname();
        this.phoneNumber = teacherInsertRequest.getPhoneNumber();
    }


}
