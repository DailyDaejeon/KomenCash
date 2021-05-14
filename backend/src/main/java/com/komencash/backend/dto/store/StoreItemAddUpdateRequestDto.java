package com.komencash.backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemAddUpdateRequestDto {

    private int id;
    private String name;
    private int price;
    private int groupId;
}
