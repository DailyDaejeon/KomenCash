package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProductHistoryRepository extends JpaRepository<FinancialProductHistory, Integer> {

    List<FinancialProductHistory> findByFinancialProduct_Id(int productId);
    List<FinancialProductHistory> findByStudent_Id(int studentId);

}