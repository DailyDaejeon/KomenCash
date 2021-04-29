package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.bank.AccountHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private String nickname;
    private int student_id;
    private List<AccountHistory> accountHistory;

    public AccountResponseDto(int student_id, String nickname, List<AccountHistory> accountHistory) {

    }
}
