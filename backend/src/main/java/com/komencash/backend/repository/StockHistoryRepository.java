package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
}