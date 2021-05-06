package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberAddRequestHistoryRepository extends JpaRepository<GroupMemberAddRequestHistory, Integer> {

    GroupMemberAddRequestHistory findByStudent_Id(int id);
}