package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.dto.request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
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

    public List<StudentResponseDto> getStudent(int group_id){
        System.out.println(studentRepository.findById(2).get().getNickname());
        List<Student> student = studentRepository.findAllByJob_Group_Id(group_id);
        List<StudentResponseDto> students = new ArrayList<>();
        student.forEach(s -> students.add(new StudentResponseDto(s.getId(), s.getNickname(), s.getJob())) );
        return students;
    }

    public StudentDetailResponseDto getStudentDetail(int studentId) {
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


    public List<GroupMemberAddRequestDto> getStudentJoinRequest(int group_id) {
        List<Student> tst = studentRepository.findAllByJob_Group_Id(group_id);
        List<GroupMemberAddRequestDto> list = new ArrayList<>();
        tst.forEach(s ->{
            GroupMemberAddRequestHistory dto = groupMemberAddRequestHistoryRepository.findByStudent_Id(s.getId());
            System.out.println(dto.getStudent().getNickname()+" , "+dto.getAccept());

            if(dto.getAccept()==Accept.before_confirm){
                list.add(new GroupMemberAddRequestDto(dto.getId(), dto.getAccept(), dto.getStudent().getId(), dto.getStudent().getNickname()));
            }
        });


        return list;
    }

    public void addStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudent_Id(studentId);
        addRequest.updateAccept();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }
    public void rejectStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudent_Id(studentId);
        addRequest.updateReject();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }


    public void resetPw(int studentId) {
        Optional<Student> stu = studentRepository.findById(studentId);
        stu.get().updatePw();
        studentRepository.save(stu.get());
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

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }


}
