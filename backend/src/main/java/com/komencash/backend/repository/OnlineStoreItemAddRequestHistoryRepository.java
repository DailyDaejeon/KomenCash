package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineStoreItemAddRequestHistoryRepository extends JpaRepository<OnlineStoreItemAddRequestHistory, Integer> {
}