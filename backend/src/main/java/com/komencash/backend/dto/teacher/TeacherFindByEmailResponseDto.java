package com.komencash.backend.dto.teacher;

import com.komencash.backend.entity.teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherFindByEmailResponseDto {

    private int id;
    private String email;

    public TeacherFindByEmailResponseDto(Teacher teacher){
        this.id = teacher.getId();
        this.email = teacher.getEmail();
    }
}
