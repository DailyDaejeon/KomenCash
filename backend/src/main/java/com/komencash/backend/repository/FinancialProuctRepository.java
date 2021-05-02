package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProuctRepository extends JpaRepository<FinancialProduct, Integer> {
}