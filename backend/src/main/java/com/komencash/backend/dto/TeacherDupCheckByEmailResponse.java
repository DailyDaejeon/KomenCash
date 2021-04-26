package com.komencash.backend.dto;

import com.komencash.backend.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDupCheckByEmailResponse {

    private int id;
    private String email;

    public TeacherDupCheckByEmailResponse(Teacher teacher){
        this.id = teacher.getId();
        this.email = teacher.getEmail();
    }
}
