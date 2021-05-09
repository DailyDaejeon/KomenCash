package com.komencash.backend.repository;

import com.komencash.backend.entity.tax.TaxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxHistoryRepository extends JpaRepository<TaxHistory, Integer> {

    List<TaxHistory> findByGroup_Id(int groupId);
}