package com.komencash.backend.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDealHistoryFindHoldingStatusDto {

    private int stockId;
    private String stockName;
    private int curPrice;
    private double avgDealPrice;
    private int curAmount;
    private double changePercent;

}
