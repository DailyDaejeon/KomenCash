package com.komencash.backend.entity.request_history;

import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salary_payment_request_history")
public class SalaryPaymentRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "salary")
    private int salary;

    @Column(name = "tax_loss")
    private int taxLoss;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
