package com.komencash.backend.repository;

import com.komencash.backend.entity.law.Law;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LawRepository extends JpaRepository<Law, Integer> {

    Optional<List<Law>> findByGroup_Id(int group_id);
}
