package com.komencash.backend.repository;

import com.komencash.backend.entity.law.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LawRepository extends JpaRepository<Law, Integer> {

    List<Law> findByGroup_Id(int groupId);

    @Query(value = "select l.lawType from Law l where l.group.id = :groupId group by l.lawType")
    List<String> findByGroup_IdGroupByLawType(int groupId);

}
