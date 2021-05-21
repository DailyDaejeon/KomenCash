package com.komencash.backend.repository;

import com.komencash.backend.entity.statistic.StatisticList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticListRepository extends JpaRepository<StatisticList, Integer> {

    public List<StatisticList> findByGroup_Id(int groupId);
}