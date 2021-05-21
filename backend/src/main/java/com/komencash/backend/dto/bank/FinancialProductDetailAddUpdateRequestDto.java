package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.financial.FinancialProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductDetailAddUpdateRequestDto {
    private int id;
    private int period;
    private int creditGrade;
    private double rate;
    private FinancialProduct financialProduct;

    public FinancialProductDetailAddUpdateRequestDto(int period, int creditGrade, double rate) {
        this.period = period;
        this.creditGrade = creditGrade;
        this.rate = rate;
    }
}
