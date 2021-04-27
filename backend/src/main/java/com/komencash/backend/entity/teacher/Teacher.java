package com.komencash.backend.entity.teacher;

import com.komencash.backend.dto.teacher.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.teacher.TeacherPasswordUpdateRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void updateTeacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        this.email = teacherInsertUpdateRequest.getEmail();
        this.password = teacherInsertUpdateRequest.getPassword();
        this.nickname = teacherInsertUpdateRequest.getNickname();
        this.phoneNumber = teacherInsertUpdateRequest.getPhoneNumber();
    }

    public void updatePasswordTeacher(TeacherPasswordUpdateRequest teacherPasswordUpdateRequest) {
        this.password = teacherPasswordUpdateRequest.getPassword();
    }
}
