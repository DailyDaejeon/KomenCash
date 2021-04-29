package com.komencash.backend.service;

import com.komencash.backend.dto.job.JobDetailResponse;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.job.JobStudentResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.JobRepository;
import com.komencash.backend.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    StudentRepository studentRepository;
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


    public JobDetailResponse findJobDetailById(int jobId){
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null) return null;

        List<Student> students = studentRepository.findByJob_Id(jobId);
        List<JobStudentResponse> jobStudentResponses = new ArrayList<>();
        for(Student student : students) jobStudentResponses.add(new JobStudentResponse(student));

        return new JobDetailResponse(job, jobStudentResponses);
    }
}
