package com.komencash.backend.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryInsertRequest {

    private int stockId;
    private int price;

}
