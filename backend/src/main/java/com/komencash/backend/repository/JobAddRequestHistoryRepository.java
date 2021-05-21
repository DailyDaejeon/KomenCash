package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.JobAddRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobAddRequestHistoryRepository extends JpaRepository<JobAddRequestHistory, Integer> {

    List<JobAddRequestHistory> findByStudent_Job_Group_Id(int groupId);
}