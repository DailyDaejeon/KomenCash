package com.komencash.backend.repository;

import com.komencash.backend.entity.bank.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {
    List<AccountHistory> findByStudent_Job_Group_Id(int groupId);

    List<AccountHistory> findByStudent_Id(int id);
}
