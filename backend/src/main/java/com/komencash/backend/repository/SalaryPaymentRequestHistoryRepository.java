package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.SalaryPaymentRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryPaymentRequestHistoryRepository extends JpaRepository<SalaryPaymentRequestHistory, Integer> {
    List<SalaryPaymentRequestHistory> findByStudent_Job_Group_Id(int groupId);
}
