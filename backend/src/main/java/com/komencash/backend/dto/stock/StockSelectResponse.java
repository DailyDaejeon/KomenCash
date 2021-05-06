package com.komencash.backend.dto.stock;

import com.komencash.backend.entity.stock.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSelectResponse {

    private int id;
    private int prePrice;
    private int price;
    private String name;
    private String hint;

    public StockSelectResponse (Stock stock, int price, int prePrice){
        this.id = stock.getId();
        this.name = stock.getName();
        this.hint = stock.getHint();
        this.price = price;
        this.prePrice = prePrice;
    }

}
