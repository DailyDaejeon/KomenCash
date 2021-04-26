package com.komencash.backend.dto;

import com.komencash.backend.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAuthByPhoneResponse {

    private int authNum;
    private String email;


}
