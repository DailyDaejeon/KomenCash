package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProductPurchaseRepository extends JpaRepository<FinancialProductPurchase, Integer> {
    List<FinancialProductPurchase> findByFinancialProductHistory_FinancialProduct_Id(int productId);
}