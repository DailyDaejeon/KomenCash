package com.komencash.backend.entity;

import com.komencash.backend.dto.TeacherInsertUpdateRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue
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


    public Teacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        this.id = teacherInsertUpdateRequest.getId();
        this.email = teacherInsertUpdateRequest.getEmail();
        this.password = teacherInsertUpdateRequest.getPassword();
        this.nickname = teacherInsertUpdateRequest.getNickname();
        this.phoneNumber = teacherInsertUpdateRequest.getPhoneNumber();
    }
}
