package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateIssueRequestHistoryRepository extends JpaRepository<CertificateIssueRequestHistory, Integer> {
}
