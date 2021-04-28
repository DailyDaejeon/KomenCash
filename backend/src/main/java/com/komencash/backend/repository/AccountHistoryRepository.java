package com.komencash.backend.repository;

import com.komencash.backend.entity.payment.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {
}
