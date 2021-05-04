package com.komencash.backend.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInsertUpdateRequest {

    private String name;
    private String hint;
    private int group_id;
}
