package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.financial.FinancialProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductDetailResponse {

    private int id;
    private int creditGrade;
    private int period;
    private double rate;

    public FinancialProductDetailResponse(FinancialProductDetail financialProductDetail){
        this.id = financialProductDetail.getId();
        this.creditGrade = financialProductDetail.getCreditGrade();
        this.period = financialProductDetail.getPeriod();
        this.rate = financialProductDetail.getRate();
    }
}
