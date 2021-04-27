package com.komencash.backend.entity.financial;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "financial_product_history")
public class FinancialProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "principal")
    private int principal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "financial_product_id")
    private FinancialProduct financialProduct;

}
