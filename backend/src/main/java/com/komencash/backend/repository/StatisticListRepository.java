package com.komencash.backend.repository;

import com.komencash.backend.entity.statistic.StatisticList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticListRepository extends JpaRepository<StatisticList, Integer> {
}