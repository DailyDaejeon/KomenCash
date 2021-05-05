package com.komencash.backend.repository;

import com.komencash.backend.entity.financial.FinancialProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProductDetailRepository extends JpaRepository<FinancialProductDetail, Integer> {
    List<FinancialProductDetail> findByFinancialProduct_Group_Id(int groupId);
    List<FinancialProductDetail> findByFinancialProduct_Id(int productId);
}