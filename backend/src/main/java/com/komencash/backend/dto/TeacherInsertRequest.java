package com.komencash.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInsertRequest {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

}
