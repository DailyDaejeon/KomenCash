package com.komencash.backend.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherFindByPhoneNumberResponseDto {

    private String authNum;
    private String email;


}
