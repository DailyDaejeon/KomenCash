package com.komencash.backend.repository;

import com.komencash.backend.entity.payment.TaxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxHistoryRepository extends JpaRepository<TaxHistory, Integer> {
}