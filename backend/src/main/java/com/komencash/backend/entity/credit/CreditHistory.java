package com.komencash.backend.entity.credit;

import com.komencash.backend.entity.Student;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_history")
public class CreditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("id")
    private int id;

    @Column("content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime")
    private Date datetime;

    @Column(name = "point_change")
    private int pointChange;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
