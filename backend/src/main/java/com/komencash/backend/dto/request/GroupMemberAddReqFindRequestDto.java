package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberAddReqFindRequestDto {
    private int id;
    private Accept accept;
    private int student_id;
    private String nickname;

    public GroupMemberAddReqFindRequestDto(Accept accept, int student_id, String nickname) {
        this.accept = accept;
        this.student_id = student_id;
        this.nickname = nickname;
    }
}
