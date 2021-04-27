package com.komencash.backend.entity.payment;

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
@Table(name = "account_history")
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "balance_change")
    private int balanceChange;

    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
