package com.komencash.backend.dto;

import com.komencash.backend.entity.Group;
import com.komencash.backend.entity.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDetailResponseDto {

    // Job 생기면 바꿔야하
//    private Job job;

//    public StudentDetailResponseDto(String nickname, String password, Group group, Job job) {
//        this.nickname = nickname;
//        this.password = password;
//        this.group = group;
//        this.job = job;
//    }
    private Student student;

    public StudentDetailResponseDto(Student student) {
        this.student = student;
    }
}

