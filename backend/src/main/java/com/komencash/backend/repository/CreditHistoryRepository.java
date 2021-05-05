package com.komencash.backend.repository;

import com.komencash.backend.entity.credit.CreditHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Integer> {
    List<CreditHistory> findByStudent_Id(int studentId);
}
