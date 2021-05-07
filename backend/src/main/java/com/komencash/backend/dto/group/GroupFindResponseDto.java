package com.komencash.backend.dto.group;

import com.komencash.backend.entity.group.Group;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupFindResponseDto {

    private Group group;
    private int studentCnt;

    public GroupFindResponseDto(Group group, int studentCnt){
        this.group = group;
        this.studentCnt = studentCnt;
    }
}
