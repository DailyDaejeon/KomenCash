package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistoryFindResponseDto {
    private int balance;
    private int balanceChange;
    private String content;
    private Date createdDate;

    public AccountHistoryFindResponseDto(int balance, int balanceChange, String content) {
        this.balance = balance;
        this.balanceChange = balanceChange;
        this.content = content;
    }

}
