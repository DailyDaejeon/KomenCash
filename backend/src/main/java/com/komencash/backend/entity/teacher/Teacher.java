package com.komencash.backend.entity.teacher;

import com.komencash.backend.dto.teacher.TeacherAddUpdateRequestDto;
import com.komencash.backend.dto.teacher.TeacherUpdatePasswordRequestDto;
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


    public Teacher(TeacherAddUpdateRequestDto teacherAddUpdateRequestDto) {
        this.id = teacherAddUpdateRequestDto.getId();
        this.email = teacherAddUpdateRequestDto.getEmail();
        this.password = teacherAddUpdateRequestDto.getPassword();
        this.nickname = teacherAddUpdateRequestDto.getNickname();
        this.phoneNumber = teacherAddUpdateRequestDto.getPhoneNumber();
    }

    public void updateTeacher(TeacherAddUpdateRequestDto teacherAddUpdateRequestDto) {
        this.email = teacherAddUpdateRequestDto.getEmail();
        this.password = teacherAddUpdateRequestDto.getPassword();
        this.nickname = teacherAddUpdateRequestDto.getNickname();
        this.phoneNumber = teacherAddUpdateRequestDto.getPhoneNumber();
    }

    public void updatePasswordTeacher(TeacherUpdatePasswordRequestDto teacherUpdatePasswordRequestDto) {
        this.password = teacherUpdatePasswordRequestDto.getPassword();
    }
}
