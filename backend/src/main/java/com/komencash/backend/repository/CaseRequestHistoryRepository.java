package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.CaseRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRequestHistoryRepository extends JpaRepository<CaseRequestHistory, Integer>{
}
