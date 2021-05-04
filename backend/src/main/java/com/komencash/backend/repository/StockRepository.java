package com.komencash.backend.repository;

import com.komencash.backend.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByGroup_Id(int groupId);
}