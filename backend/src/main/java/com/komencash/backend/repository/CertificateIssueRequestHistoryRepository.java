package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateIssueRequestHistoryRepository extends JpaRepository<CertificateIssueRequestHistory, Integer> {
    List<CertificateIssueRequestHistory> findAllByStudent_Id(int studentId);
}
