package com.komencash.backend.entity.financial;


import com.komencash.backend.entity.student.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "finnacial_product_customer_state")
public class FinancialProductCustomerState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "stack_money")
    private int stackMoney;

    @Column(name = "stack_count")
    private int stackCount;

    @ManyToOne
    @JoinColumn(name ="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "financial_product_history_id")
    private FinancialProductHistory financialProductHistory;



}
