package com.komencash.backend.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxHistoryInsertUpdateRequest {

    private int id;
    private int balanceChange;
    private String content;
    private int groupId;

}
