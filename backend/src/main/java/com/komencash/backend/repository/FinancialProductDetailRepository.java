package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialProductDetailRepository extends JpaRepository<FinancialProductDetail, Integer> {
}