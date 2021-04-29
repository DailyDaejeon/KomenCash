package com.komencash.backend.service;

import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    GroupRepository groupRepository;

    public List<JobSelectResponse> findJobByGroupId(int groupId){
        List<JobSelectResponse> jobSelectResponses = new ArrayList<>();

        List<Job> jobs = jobRepository.findByGroup_id(groupId);
        for(Job job : jobs) jobSelectResponses.add(new JobSelectResponse(job));

        return jobSelectResponses;
    }


    public boolean saveJob(JobInsertUpdateRequest jobInsertUpdateRequest) {
        Group group = groupRepository.findById(jobInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        Job job = new Job(jobInsertUpdateRequest, group);
        jobRepository.save(job);
        return true;
    }
}
