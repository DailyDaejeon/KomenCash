package com.komencash.backend.dto.teacher;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAddUpdateRequestDto {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

}
