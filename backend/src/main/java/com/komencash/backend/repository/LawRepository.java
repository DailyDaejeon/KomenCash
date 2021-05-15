package com.komencash.backend.repository;

import com.komencash.backend.entity.law.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LawRepository extends JpaRepository<Law, Integer> {

    List<Law> findByGroup_Id(int groupId);

    @Query(value = "select l from Law l group by l.lawType")
    List<Law> findByGroup_IdGroupByLawType(int groupId);

    List<Law> findByLawType(String lawType);
}
