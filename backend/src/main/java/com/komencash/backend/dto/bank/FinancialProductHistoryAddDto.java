package com.komencash.backend.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductHistoryAddDto {
    private int id;
    private int principal;
    private int financialProductDetailId;
    private int studentId;

    public FinancialProductHistoryAddDto(int financialProductDetailId, int studentId) {
        this.financialProductDetailId = financialProductDetailId;
        this.studentId = studentId;
    }

    public FinancialProductHistoryAddDto(int id) {
        this.id = id;
    }
}
