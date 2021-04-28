package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRequestHistoryRepository extends JpaRepository<ResumeRequestHistory, Integer> {
}