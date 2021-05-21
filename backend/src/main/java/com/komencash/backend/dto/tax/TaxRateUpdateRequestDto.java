package com.komencash.backend.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxRateUpdateRequestDto {
    private double taxRate;
    private int groupId;
}
