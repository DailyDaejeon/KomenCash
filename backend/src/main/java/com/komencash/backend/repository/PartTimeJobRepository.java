package com.komencash.backend.repository;

import com.komencash.backend.entity.job.PartTimeJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartTimeJobRepository extends JpaRepository<PartTimeJob, Integer> {

    List<PartTimeJob> findByGroup_Id(int groupId);
}