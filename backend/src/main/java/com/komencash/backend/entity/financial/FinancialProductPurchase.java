package com.komencash.backend.entity.financial;

import com.komencash.backend.entity.Student;
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
@Table(name = "financial_product_purchase")
public class FinancialProductPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "financial_product_history_id")
    private FinancialProductHistory financialProductHistory;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}