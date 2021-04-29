package com.komencash.backend.dto.job;

import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobStudentResponse {

    private int studentId;
    private String studentNickName;

    public JobStudentResponse(Student student) {
        this.studentId = student.getId();
        this.studentNickName = student.getNickname();
    }
}
