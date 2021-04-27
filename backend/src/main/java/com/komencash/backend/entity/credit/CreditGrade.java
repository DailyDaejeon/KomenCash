package com.komencash.backend.entity.credit;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_grade")
public class CreditGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "grade")
    private int grade;

    @Column(name = "max_point")
    private int maxPoint;

    @Column(name = "min_point")
    private int minPoint;

}
