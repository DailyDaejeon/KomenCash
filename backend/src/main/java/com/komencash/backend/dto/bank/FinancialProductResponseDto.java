package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinancialProductResponseDto {
    private String name;
    private int groupId;

    public FinancialProductResponseDto(String name, int group_id) {
        this.name = name;
        this.groupId = group_id;
    }
}
