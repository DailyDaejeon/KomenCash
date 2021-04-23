package com.komencash.backend.dto;

import com.komencash.backend.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSelectResponse {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    public TeacherSelectResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.nickname = teacher.getNickname();
        this.phoneNumber = teacher.getPhoneNumber();
    }
}
