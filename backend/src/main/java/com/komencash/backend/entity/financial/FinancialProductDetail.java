package com.komencash.backend.entity.financial;

import com.komencash.backend.dto.bank.FinancialProductDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "financial_product_detail")
public class FinancialProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "period")
    private int period;

    @Column(name = "credit_grade")
    private int creditGrade;

    @Column(name = "rate")
    private double rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "financial_product_id")
    private FinancialProduct financialProduct;

    public FinancialProductDetail(FinancialProductDetailRequest dto, FinancialProduct financialProduct) {
        this.period = dto.getPeriod();
        this.creditGrade = dto.getCreditGrade();
        this.rate = dto.getRate();
        this.financialProduct = financialProduct;
    }
}
