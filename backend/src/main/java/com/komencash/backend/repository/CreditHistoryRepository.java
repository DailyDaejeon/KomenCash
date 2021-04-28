package com.komencash.backend.repository;

import com.komencash.backend.entity.credit.CreditHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Integer> {
}
