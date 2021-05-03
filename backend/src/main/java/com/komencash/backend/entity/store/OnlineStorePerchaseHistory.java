package com.komencash.backend.entity.store;

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
@Table(name = "online_sotre_perchase_history")
public class OnlineStorePerchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "perchase_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perchaseDatetime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
