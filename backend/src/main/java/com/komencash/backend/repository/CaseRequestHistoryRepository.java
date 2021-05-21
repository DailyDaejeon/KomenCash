package com.komencash.backend.repository;

import com.komencash.backend.entity.request_history.CaseRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRequestHistoryRepository extends JpaRepository<CaseRequestHistory, Integer>{

    List<CaseRequestHistory> findByPolice_Job_Group_Id(int groupId);
    List<CaseRequestHistory> findByStudent_Id(int studentId);
}
