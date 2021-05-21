package com.komencash.backend.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDealHistoryAddRequestDto {

    private int stockId;
    private int price;
    private int amount;
    private int studentId;

}
