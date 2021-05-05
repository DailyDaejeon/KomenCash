package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductDetailUpdateRequest {

    private int id;
    private int creditGrade;
    private int period;
    private double rate;
}
