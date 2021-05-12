package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.certificate.CertificateIssueAddRequestDto;
import com.komencash.backend.entity.student.Student;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public CertificateIssueRequestHistory(Certificate certificate, Student student){
        this.accept = Accept.accept;
    }

    public void updateCertificateAccept(Accept accept){
        this.accept = accept;
    }

}
