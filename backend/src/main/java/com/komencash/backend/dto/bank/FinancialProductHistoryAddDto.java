package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductHistoryAddDto {
    private int principal;
    private int financialProductDetailId;
    private int studentId;

}
