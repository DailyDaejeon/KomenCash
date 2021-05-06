package com.komencash.backend.dto.stock;

import com.komencash.backend.entity.stock.StockDealHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDealHistoryResponse {

    private int id;
    private int price;
    private int amount;
    private int stockId;
    private String stockName;
    private Date createdDate;

    public StockDealHistoryResponse(StockDealHistory stockDealHistory){
        this.id = stockDealHistory.getId();
        this.price = stockDealHistory.getPrice();
        this.amount = stockDealHistory.getAmount();
        this.stockId = stockDealHistory.getStock().getId();
        this.stockName = stockDealHistory.getStock().getName();
        this.createdDate = stockDealHistory.getCreatedDate();
    }
}
