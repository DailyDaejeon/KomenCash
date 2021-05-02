package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductDetailResponseDto {
    private int period;
    private int creditGrade;
    private double rate;
}
