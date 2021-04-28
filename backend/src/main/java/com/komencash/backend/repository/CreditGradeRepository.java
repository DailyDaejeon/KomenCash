package com.komencash.backend.repository;

import com.komencash.backend.entity.credit.CreditGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditGradeRepository extends JpaRepository<CreditGrade, Integer> {
}
