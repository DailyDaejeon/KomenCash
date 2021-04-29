package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRequestHistoryRepository extends JpaRepository<ResumeRequestHistory, Integer> {

    List<ResumeRequestHistory> findByStudent_Job_Group_Id(int groupId);
}