package com.komencash.backend.dto.tax;

import com.komencash.backend.entity.tax.TaxHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxHistoryFindResponseDto {

    private int id;
    private int balanceChange;
    private int balance;
    private String content;
    private Date createdDate;

    public TaxHistoryFindResponseDto(TaxHistory taxHistory) {
        this.id = taxHistory.getId();
        this.balanceChange = taxHistory.getBalanceChange();
        this.balance = taxHistory.getBalance();
        this.content = taxHistory.getContent();
        this.createdDate = taxHistory.getCreatedDate();
    }
}
