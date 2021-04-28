package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LawAddRequestHistoryRepository extends JpaRepository<LawAddRequestHistory, Integer> {

    List<LawAddRequestHistory> findByStudent_Id(int student_id);
}