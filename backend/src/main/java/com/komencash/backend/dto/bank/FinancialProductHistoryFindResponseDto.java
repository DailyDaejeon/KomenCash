package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.financial.FinancialProduct;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.financial.FinancialProductHistory;
import com.komencash.backend.entity.financial.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductHistoryFindResponseDto {

    private int id;
    private int principal;
    private Date startDate;
    private Date endDate;
    private Status status;
    private double rate;
    private int financialProductId;
    private String financialProductName;

    public FinancialProductHistoryFindResponseDto(FinancialProductHistory financialProductHistory){
        this.id = financialProductHistory.getId();
        this.principal = financialProductHistory.getPrincipal();
        this.startDate = financialProductHistory.getStartDate();
        this.endDate = financialProductHistory.getEndDate();
        this.status = financialProductHistory.getStatus();
        this.financialProductId = financialProductHistory.getFinancialProductDetail().getFinancialProduct().getId();
        this.financialProductName = financialProductHistory.getFinancialProductDetail().getFinancialProduct().getName();
    }

    public FinancialProductHistoryFindResponseDto(FinancialProductHistory financialProductHistory, double rate) {
        this.id = financialProductHistory.getId();
        this.principal = financialProductHistory.getPrincipal();
        this.startDate = financialProductHistory.getStartDate();
        this.endDate = financialProductHistory.getEndDate();
        this.status = financialProductHistory.getStatus();
        this.rate = rate;
        this.financialProductId = financialProductHistory.getFinancialProductDetail().getFinancialProduct().getId();
        this.financialProductName = financialProductHistory.getFinancialProductDetail().getFinancialProduct().getName();

    }
}
