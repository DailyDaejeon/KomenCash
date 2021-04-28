package com.komencash.backend.repository;

import com.komencash.backend.entity.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}