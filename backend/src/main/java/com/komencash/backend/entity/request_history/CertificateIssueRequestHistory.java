package com.komencash.backend.entity.request_history;

import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.certificate.Certificate;
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
@Table(name = "certificate_issue_request_history")
public class CertificateIssueRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated
    @Column(name = "accpet")
    private Accept accept;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}