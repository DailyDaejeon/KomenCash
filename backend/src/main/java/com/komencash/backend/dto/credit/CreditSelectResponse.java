package com.komencash.backend.dto.credit;

import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditSelectResponse {

    private int studentId;
    private String studentNickname;
    private int grade;
    private int creditPoint;

    public CreditSelectResponse(Student student, int grade, int point) {
        this.studentId = student.getId();
        this.studentNickname = student.getNickname();
        this.grade = grade;
        this.creditPoint = point;
    }
}
