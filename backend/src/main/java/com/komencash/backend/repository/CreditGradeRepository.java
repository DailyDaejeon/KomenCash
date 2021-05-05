package com.komencash.backend.repository;

import com.komencash.backend.entity.credit.CreditGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditGradeRepository extends JpaRepository<CreditGrade, Integer> {
    @Query(value = "select c from CreditGrade c where c.maxPoint > :point and c.minPoint <= :point")
    CreditGrade findByPoint(@Param("point") int point);
}
