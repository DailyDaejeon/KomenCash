package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.dto.request.GroupMemberAddReqFindRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentFindResponseDto;
import com.komencash.backend.dto.student.StudentUpdateJobRequest;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupMemberAddRequestHistoryRepository groupMemberAddRequestHistoryRepository;

    @Autowired
    CertificateIssueRequestHistoryRepository certificateIssueRequestHistoryRepository;

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    JobRepository jobRepository;


    public List<StudentFindResponseDto> findStudentListByGroupId(int groupId){
        List<StudentFindResponseDto> studentFindResponseDtos = new ArrayList<>();
        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);

        students.forEach(student ->
                studentFindResponseDtos.add(new StudentFindResponseDto(student.getId(), student.getNickname(), student.getJob())));
        return studentFindResponseDtos;
    }


    public StudentDetailResponseDto findStudentDetailByStudentId(int studentId) {
        List<CertificateSelectResponse> certificateSelectResponseList = new ArrayList<>();
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) return null;

        List<CertificateIssueRequestHistory> certificateIssueRequestHistories = certificateIssueRequestHistoryRepository.findAllByStudent_Id(studentId);
        certificateIssueRequestHistories.forEach(certificateIssueRequestHistory -> {
            if(certificateIssueRequestHistory.getAccept().equals(Accept.accept))
                certificateSelectResponseList.add(new CertificateSelectResponse(certificateIssueRequestHistory.getCertificate()));
        });

        return new StudentDetailResponseDto(student, certificateSelectResponseList);
    }


    public List<GroupMemberAddReqFindRequestDto> findGroupMemberAddReqByGroupId(int groupId) {
        List<GroupMemberAddReqFindRequestDto> groupMemberAddReqFindRequestDtos = new ArrayList<>();

        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);
        if(students.size() == 0) return null;

        students.forEach(student ->{
            GroupMemberAddRequestHistory groupMemberAddRequestHistory = groupMemberAddRequestHistoryRepository.findByStudent_Id(student.getId());

            if(groupMemberAddRequestHistory.getAccept() == Accept.before_confirm){
                groupMemberAddReqFindRequestDtos.add(
                        new GroupMemberAddReqFindRequestDto(
                        groupMemberAddRequestHistory.getId(),
                        groupMemberAddRequestHistory.getAccept(),
                        groupMemberAddRequestHistory.getStudent().getId(),
                        groupMemberAddRequestHistory.getStudent().getNickname()
                        )
                );
            }
        });

        return groupMemberAddReqFindRequestDtos;
    }


    public boolean updateGroupMemberAddReqAccept(int requestId) {
        GroupMemberAddRequestHistory groupMemberAddRequestHistory = groupMemberAddRequestHistoryRepository.findById(requestId).orElse(null);
        if (groupMemberAddRequestHistory == null) return false;

        groupMemberAddRequestHistory.updateAccept();
        groupMemberAddRequestHistoryRepository.save(groupMemberAddRequestHistory);
        return true;
    }


    public boolean updateGroupMemberAddReqReject(int requestId) {
        GroupMemberAddRequestHistory groupMemberAddRequestHistory = groupMemberAddRequestHistoryRepository.findById(requestId).orElse(null);
        if(groupMemberAddRequestHistory == null) return false;

        groupMemberAddRequestHistory.updateReject();
        groupMemberAddRequestHistoryRepository.save(groupMemberAddRequestHistory);
        return true;
    }


    public boolean updateStudentPasswordInit(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        student.get().updatePw();
        studentRepository.save(student.get());
        return true;
    }


    public boolean updateStudentJobFire(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) return false;

        Job job = jobRepository.findByName("무직");
        if(job == null) return false;

        student.updateJob(job);
        studentRepository.save(student);
        return true;
    }


    public boolean updateStudentJob(StudentUpdateJobRequest studentUpdateJobRequest){
        Student student = studentRepository.findById(studentUpdateJobRequest.getStudentId()).orElse(null);
        if(student == null) return false;

        Job job = jobRepository .findById(studentUpdateJobRequest.getJobId()).orElse(null);
        if(job == null) return false;

        student.updateJob(job);
        studentRepository.save(student);
        return true;
    }


    public boolean deleteStudent(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) return false;

        studentRepository.delete(student);
        return true;
    }


}
