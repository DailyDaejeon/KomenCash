package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialProductHistoryRepository extends JpaRepository<FinancialProductHistory, Integer> {
}