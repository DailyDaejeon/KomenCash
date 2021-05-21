package com.komencash.backend.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentJoinRequestDto {
    private int studentId;
    private String nickname;
    private String password;
    private String code;


}
