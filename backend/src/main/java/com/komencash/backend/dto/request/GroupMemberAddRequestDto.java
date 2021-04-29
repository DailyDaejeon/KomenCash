package com.komencash.backend.dto.request;

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
