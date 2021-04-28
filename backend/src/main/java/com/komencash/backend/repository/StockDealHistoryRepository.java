package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.StockDealHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDealHistoryRepository extends JpaRepository<StockDealHistory, Integer> {
}