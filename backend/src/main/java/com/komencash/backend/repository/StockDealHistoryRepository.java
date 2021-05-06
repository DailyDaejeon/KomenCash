package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.StockDealHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockDealHistoryRepository extends JpaRepository<StockDealHistory, Integer> {

    List<StockDealHistory> findByStudent_Id(int studentId);
}