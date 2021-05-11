package com.komencash.backend.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxHistoryAddUpdateRequestDto {

    private int id;
    private int balanceChange;
    private String content;
    private int groupId;

    public TaxHistoryAddUpdateRequestDto(int balanceChange, String content, int groupId) {
        this.balanceChange = balanceChange;
        this.content = content;
        this.groupId = groupId;
    }
}
