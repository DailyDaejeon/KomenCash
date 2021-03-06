package com.komencash.backend.dto.stock;

import com.komencash.backend.entity.stock.StockHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryFindListResponseDto {

    private int id;
    private int price;
    private Date createdDate;

    public StockHistoryFindListResponseDto(StockHistory stockHistory){
        this.id = stockHistory.getId();
        this.price = stockHistory.getPrice();
        this.createdDate = stockHistory.getCreatedDate();
    }
}
