package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {

    List<StockHistory> findByStock_Id(int stockId);
}