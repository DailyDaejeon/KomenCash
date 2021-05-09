package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFindResponseDto {
    private int id;
    private String nickname;
    private int student_id;
    private List<AccountHistoryFindResponseDto> accountHistory;

    public AccountFindResponseDto(int student_id, String nickname, List<AccountHistoryFindResponseDto> accountHistory) {
        this.student_id = student_id;
        this.nickname = nickname;
        this.accountHistory = accountHistory;
    }
}
