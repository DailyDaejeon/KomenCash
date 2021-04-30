package com.komencash.backend.service;

import com.komencash.backend.dto.job.*;
import com.komencash.backend.dto.request.JobAddReqSelectResponse;
import com.komencash.backend.dto.request.ResumeDetailSelectResponse;
import com.komencash.backend.dto.request.ResumeSelectResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.job.PartTimeJob;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.JobAddRequestHistory;
import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    PartTimeJobRepository partTimeJobRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    JobAddRequestHistoryRepository jobAddRequestHistoryRepository;
    @Autowired
    ResumeRequestHistoryRepository resumeRequestHistoryRepository;

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


    public boolean updateJob(JobInsertUpdateRequest jobInsertUpdateRequest) {
        Job job = jobRepository.findById(jobInsertUpdateRequest.getId()).orElse(null);
        if(job == null) return false;

        job.updateJob(jobInsertUpdateRequest);
        jobRepository.save(job);
        return true;
    }


    public boolean deleteJob(int jobId){
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null) return false;

        // 삭제되는 직업과 관련된 학생들은 무직으로 바꿔준다.
        Job defaultJob = jobRepository.findByName("무직");
        List<Student> students = studentRepository.findByJob_Id(jobId);
        for(Student student : students) {
            student.updateJob(defaultJob);
            studentRepository.save(student);
        }

        jobRepository.delete(job);
        return true;
    }


    public List<JobAddReqSelectResponse> findJobAddRequestByGroupId (int groupId) {
        List<JobAddReqSelectResponse> jobAddReqSelectResponses = new ArrayList<>();

        List<JobAddRequestHistory> jobAddRequestHistories = jobAddRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);
        for(JobAddRequestHistory jobAddRequestHistory : jobAddRequestHistories) {
            if(!jobAddRequestHistory.getAccept().equals(Accept.before_confirm)) continue;
            jobAddReqSelectResponses.add(new JobAddReqSelectResponse(jobAddRequestHistory));
        }
        return jobAddReqSelectResponses;
    }


    public List<ResumeSelectResponse> findResumeListByGroupId(int groupId) {
        List<ResumeSelectResponse> resumeSelectResponses = new ArrayList<>();

        List<ResumeRequestHistory> resumeRequestHistories = resumeRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);
        for (ResumeRequestHistory resumeRequestHistory : resumeRequestHistories) {
            if(!resumeRequestHistory.getAccept().equals(Accept.before_confirm)) continue;
            resumeSelectResponses.add(new ResumeSelectResponse(resumeRequestHistory));
        }
        return resumeSelectResponses;
    }


    public ResumeDetailSelectResponse findResumeById(int resumeId) {
        ResumeRequestHistory resumeRequestHistory = resumeRequestHistoryRepository.findById(resumeId).orElseGet(ResumeRequestHistory::new);
        return new ResumeDetailSelectResponse(resumeRequestHistory);
    }


    public List<PartTimeSelectResponse> findPartTimeByGroupId(int groupId) {
        List<PartTimeJob> partTimeJobs = partTimeJobRepository.findByGroup_Id(groupId);

        List<PartTimeSelectResponse> partTimeSelectResponses = new ArrayList<>();
        for(PartTimeJob partTimeJob : partTimeJobs) partTimeSelectResponses.add(new PartTimeSelectResponse(partTimeJob));

        return partTimeSelectResponses;
    }


    public boolean savePartTime(PartTimeInsertUpdateRequest partTimeInsertUpdateRequest) {
        Group group = groupRepository.findById(partTimeInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        PartTimeJob partTimeJob = new PartTimeJob(partTimeInsertUpdateRequest, group);
        partTimeJobRepository.save(partTimeJob);
        return true;
    }


    public boolean deletePartTime(int partTimeId){
        PartTimeJob partTimeJob = partTimeJobRepository.findById(partTimeId).orElse(null);
        if(partTimeJob == null) return false;

        partTimeJobRepository.delete(partTimeJob);
        return true;
    }
}
