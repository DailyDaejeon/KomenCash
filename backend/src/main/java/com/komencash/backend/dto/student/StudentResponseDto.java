package com.komencash.backend.dto.student;

import com.komencash.backend.entity.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentResponseDto {

    private List<Student> student;
    public StudentResponseDto(List<Student> students){
        this.student = students;
    }


}

