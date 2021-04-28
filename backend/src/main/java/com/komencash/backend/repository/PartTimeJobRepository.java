package com.komencash.backend.repository;

import com.komencash.backend.entity.job.PartTimeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimeJobRepository extends JpaRepository<PartTimeJob, Integer> {
}