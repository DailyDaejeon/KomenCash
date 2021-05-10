package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.SalaryPaymentRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryPaymentRequestHistoryRepository extends JpaRepository<SalaryPaymentRequestHistory, Integer> {
}
