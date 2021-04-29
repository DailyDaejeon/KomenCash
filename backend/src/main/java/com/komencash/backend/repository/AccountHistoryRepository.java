package com.komencash.backend.repository;

import com.komencash.backend.entity.bank.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {
    List<AccountHistory> findAllByStudent_Job_Group_Id(int groupId);

    List<AccountHistory> findAllByStudent_Id(int id);
}
