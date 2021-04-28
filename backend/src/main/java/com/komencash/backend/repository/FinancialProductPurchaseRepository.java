package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialProductPurchaseRepository extends JpaRepository<FinancialProductPurchase, Integer> {
}