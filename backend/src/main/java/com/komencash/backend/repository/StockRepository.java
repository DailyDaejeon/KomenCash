package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}