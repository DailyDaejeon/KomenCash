package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryAddUpdateRequestDto {

    private int studentId;
    private int balanceChange;
    private String content;

}
