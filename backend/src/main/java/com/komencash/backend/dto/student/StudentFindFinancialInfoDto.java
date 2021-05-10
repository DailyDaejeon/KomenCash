package com.komencash.backend.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFindFinancialInfoDto {
    private int studentId;
    private String studentNickname;
    private int studentCreditGrade;
    private int studentCreditPoint;


}