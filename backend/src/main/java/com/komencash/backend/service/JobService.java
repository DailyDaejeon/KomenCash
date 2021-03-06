package com.komencash.backend.service;

import com.komencash.backend.dto.job.*;
import com.komencash.backend.dto.request.*;
import com.komencash.backend.dto.student.StudentUpdateJobRequest;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.job.PartTimeJob;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.JobAddRequestHistory;
import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PartTimeJobRepository partTimeJobRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JobAddRequestHistoryRepository jobAddRequestHistoryRepository;

    @Autowired
    ResumeRequestHistoryRepository resumeRequestHistoryRepository;


    public boolean addJob(JobAddUpdateRequestDto jobAddUpdateRequestDto) {
        Group group = groupRepository.findById(jobAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Job job = new Job(jobAddUpdateRequestDto, group);
        jobRepository.save(job);
        return true;
    }


    public List<JobFindResponseDto> findJobListByGroupId(int groupId){
        List<JobFindResponseDto> jobFindResponsDtos = new ArrayList<>();

        List<Job> jobs = jobRepository.findByGroup_id(groupId);
        jobs.forEach(job -> jobFindResponsDtos.add(new JobFindResponseDto(job)));

        return jobFindResponsDtos;
    }


    public JobDetailResponse findJobDetailById(int jobId){
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null) return null;

        List<Student> students = studentRepository.findByJob_Id(jobId);
        List<JobStudentResponse> jobStudentResponses = new ArrayList<>();
        for(Student student : students) jobStudentResponses.add(new JobStudentResponse(student));

        return new JobDetailResponse(job, jobStudentResponses);
    }


    public Job findJobById(int jobId){
        return jobRepository.findById(jobId).orElse(null);
    }


    public boolean updateJob(JobAddUpdateRequestDto jobAddUpdateRequestDto) {
        Job job = jobRepository.findById(jobAddUpdateRequestDto.getId()).orElse(null);
        if(job == null) return false;

        job.updateJob(jobAddUpdateRequestDto);
        jobRepository.save(job);
        return true;
    }


    public boolean deleteJob(int jobId){
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null) return false;

        // ???????????? ????????? ????????? ???????????? ???????????? ????????????.
        Job defaultJob = jobRepository.findByNameAndGroup_Id("??????", job.getGroup().getId());
        List<Student> students = studentRepository.findByJob_Id(jobId);
        for(Student student : students) {
            student.updateJob(defaultJob);
            studentRepository.save(student);
        }

        jobRepository.delete(job);
        return true;
    }


    public boolean addJobAddReq(JobAddReqAddRequestDto jobAddReqAddRequestDto){
        Student student = studentRepository.findById(jobAddReqAddRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        JobAddRequestHistory jobAddRequestHistory = new JobAddRequestHistory(jobAddReqAddRequestDto, student);
        jobAddRequestHistoryRepository.save(jobAddRequestHistory);
        return true;
    }


    public List<JobAddReqFindResponseDto> findJobAddRequestByGroupId (int groupId) {
        List<JobAddReqFindResponseDto> jobAddReqFindResponsDtos = new ArrayList<>();

        List<JobAddRequestHistory> jobAddRequestHistories = jobAddRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);
        for(JobAddRequestHistory jobAddRequestHistory : jobAddRequestHistories) {
            if(!jobAddRequestHistory.getAccept().equals(Accept.before_confirm)) continue;
            jobAddReqFindResponsDtos.add(new JobAddReqFindResponseDto(jobAddRequestHistory));
        }
        return jobAddReqFindResponsDtos;
    }


    public boolean updateJobAddRequestAccept(JobAddReqAcceptRequestDto jobAddReqAcceptRequestDto) {
        JobAddRequestHistory jobAddRequestHistory = jobAddRequestHistoryRepository.findById(jobAddReqAcceptRequestDto.getId()).orElse(null);
        if (jobAddRequestHistory == null) return false;

        if (jobAddRequestHistory.getAccept().equals(Accept.before_confirm))
            jobAddRequestHistory.updateJobAddRequestAccept(jobAddReqAcceptRequestDto.getAccept());

        jobAddRequestHistoryRepository.save(jobAddRequestHistory);
        return true;
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


    public ResumeFindDetailResponseDto findResumeById(int resumeId) {
        ResumeRequestHistory resumeRequestHistory = resumeRequestHistoryRepository.findById(resumeId).orElseGet(ResumeRequestHistory::new);
        return new ResumeFindDetailResponseDto(resumeRequestHistory);
    }


    public boolean updateResumeAccept(ResumeUpdateAcceptRequestDto resumeUpdateAcceptRequestDto){
        ResumeRequestHistory resumeRequestHistory = resumeRequestHistoryRepository.findById(resumeUpdateAcceptRequestDto.getId()).orElse(null);
        if (resumeRequestHistory == null) return false;

        // ????????? ????????? ???????????? ???????????? ????????????.
        if(resumeRequestHistory.getAccept().equals(Accept.before_confirm)){
            resumeRequestHistory.updateResumeAccept(resumeUpdateAcceptRequestDto.getAccept());

            // ????????? ???????????? ?????????????????? ?????? ????????? ????????? ????????????.
            if(resumeUpdateAcceptRequestDto.getAccept().equals(Accept.accept)) {
                int studentId = resumeRequestHistory.getStudent().getId();
                int jobId = resumeRequestHistory.getJob().getId();
                studentService.updateStudentJob(new StudentUpdateJobRequest(studentId, jobId));
            }
        }

        resumeRequestHistoryRepository.save(resumeRequestHistory);
        return true;
    }


    public boolean addPartTime(PartTimeAddUpdateRequestDto partTimeAddUpdateRequestDto) {
        Group group = groupRepository.findById(partTimeAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        PartTimeJob partTimeJob = new PartTimeJob(partTimeAddUpdateRequestDto, group);
        partTimeJobRepository.save(partTimeJob);
        return true;
    }


    public List<PartTimeFindResponseDto> findPartTimeByGroupId(int groupId) {
        List<PartTimeJob> partTimeJobs = partTimeJobRepository.findByGroup_Id(groupId);

        List<PartTimeFindResponseDto> partTimeFindResponsDtos = new ArrayList<>();
        for(PartTimeJob partTimeJob : partTimeJobs) partTimeFindResponsDtos.add(new PartTimeFindResponseDto(partTimeJob));

        return partTimeFindResponsDtos;
    }


    public boolean deletePartTime(int partTimeId){
        PartTimeJob partTimeJob = partTimeJobRepository.findById(partTimeId).orElse(null);
        if(partTimeJob == null) return false;

        partTimeJobRepository.delete(partTimeJob);
        return true;
    }

    public boolean updateStudentJob(StudentJobUpdateDto studentJobUpdateDto) {
        Student student = studentRepository.findById(studentJobUpdateDto.getStudentId()).orElse(null);
        Job job = jobRepository.findById(studentJobUpdateDto.getJobId()).orElse(null);
        student.updateJob(job);
        studentRepository.save(student);
        return true;
    }
}
