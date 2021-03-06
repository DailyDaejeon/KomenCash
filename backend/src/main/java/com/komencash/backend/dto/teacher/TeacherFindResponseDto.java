package com.komencash.backend.dto.teacher;

import com.komencash.backend.entity.teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherFindResponseDto {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    public TeacherFindResponseDto(Teacher teacher) {
        this.id = teacher.getId();
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.nickname = teacher.getNickname();
        this.phoneNumber = teacher.getPhoneNumber();
    }
}
