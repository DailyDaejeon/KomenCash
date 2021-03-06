package com.komencash.backend.repository;

import com.komencash.backend.entity.statistic.StatisticListDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticListDetailRepository extends JpaRepository<StatisticListDetail, Integer> {
    StatisticListDetail findByStudent_IdAndStatisticList_Id(int studentId, int statisticListId);
    List<StatisticListDetail> findByStatisticList_Id(int statisticListId);
}