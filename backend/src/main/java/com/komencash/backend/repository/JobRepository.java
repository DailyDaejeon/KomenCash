package com.komencash.backend.repository;

import com.komencash.backend.entity.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    List<Job> findByGroup_id(int groupId);

    Job findByNameAndGroup_Id(String name, int groupId);
}