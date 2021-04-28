package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.JobAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAddRequestHistoryRepository extends JpaRepository<JobAddRequestHistory, Integer> {
}