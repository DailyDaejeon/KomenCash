package com.komencash.backend.repository;

import com.komencash.backend.entity.store.OnlineStorePerchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineStorePerchaseHistoryRepository extends JpaRepository<OnlineStorePerchaseHistory, Integer> {

    List<OnlineStorePerchaseHistory> findByStudent_Job_Group_Id(int groupId);
}
