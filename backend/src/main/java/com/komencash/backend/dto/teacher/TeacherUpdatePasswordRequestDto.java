package com.komencash.backend.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherUpdatePasswordRequestDto {
    private int id;
    private String password;
}
