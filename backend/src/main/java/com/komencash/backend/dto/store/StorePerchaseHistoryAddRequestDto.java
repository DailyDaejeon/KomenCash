package com.komencash.backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorePerchaseHistoryAddRequestDto {

    private int studentId;
    private int itemId;
}
