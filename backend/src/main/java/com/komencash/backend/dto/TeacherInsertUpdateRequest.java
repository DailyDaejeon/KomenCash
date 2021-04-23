package com.komencash.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInsertUpdateRequest {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

}
