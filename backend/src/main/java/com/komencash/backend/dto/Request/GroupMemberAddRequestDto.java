package com.komencash.backend.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberAddRequestDto {
    private int id;
    private String accept;
    private int student_id;
    private String nickname;

}
