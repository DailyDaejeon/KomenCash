package com.komencash.backend.service;

import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public List<JobSelectResponse> findJobByGroupId(int groupId){
        List<JobSelectResponse> jobSelectResponses = new ArrayList<>();

        List<Job> jobs = jobRepository.findByGroup_id(groupId);
        for(Job job : jobs) jobSelectResponses.add(new JobSelectResponse(job));

        return jobSelectResponses;
    }
}
