package com.komencash.backend.entity.teacher;

import com.komencash.backend.dto.teacher.TeacherAddModifyRequestDto;
import com.komencash.backend.dto.teacher.TeacherModifyPasswordRequestDto;
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


    public Teacher(TeacherAddModifyRequestDto teacherAddModifyRequestDto) {
        this.id = teacherAddModifyRequestDto.getId();
        this.email = teacherAddModifyRequestDto.getEmail();
        this.password = teacherAddModifyRequestDto.getPassword();
        this.nickname = teacherAddModifyRequestDto.getNickname();
        this.phoneNumber = teacherAddModifyRequestDto.getPhoneNumber();
    }

    public void updateTeacher(TeacherAddModifyRequestDto teacherAddModifyRequestDto) {
        this.email = teacherAddModifyRequestDto.getEmail();
        this.password = teacherAddModifyRequestDto.getPassword();
        this.nickname = teacherAddModifyRequestDto.getNickname();
        this.phoneNumber = teacherAddModifyRequestDto.getPhoneNumber();
    }

    public void updatePasswordTeacher(TeacherModifyPasswordRequestDto teacherModifyPasswordRequestDto) {
        this.password = teacherModifyPasswordRequestDto.getPassword();
    }
}
