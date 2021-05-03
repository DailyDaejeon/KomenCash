package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineStoreItemAddRequestHistoryRepository extends JpaRepository<OnlineStoreItemAddRequestHistory, Integer> {

    List<OnlineStoreItemAddRequestHistory> findByStudent_Job_Group_Id(int groupId);
}