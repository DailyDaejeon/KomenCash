package com.komencash.backend.service;

import com.komencash.backend.dto.Request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.certificate.CertificateRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
import com.komencash.backend.entity.Group;
import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.certificate.Certificate;
import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.repository.CertificateIssueRequestHistoryRepository;
import com.komencash.backend.repository.CertificateRepository;
import com.komencash.backend.repository.GroupMemberAddRequestHistoryRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupMemberAddRequestHistoryRepository groupMemberAddRequestHistoryRepository;

    @Autowired
    CertificateIssueRequestHistoryRepository certificateIssueRequestHistoryRepository;

    @Autowired
    CertificateRepository certificateRepository;

    public StudentResponseDto getStudent(int group_id){
        List<Student> student = studentRepository.findAllByJobGroup_Id(group_id);
        return new StudentResponseDto(student);
    }

    public StudentDetailResponseDto getStudentDetail(int student_id) {
        Student student = studentRepository.findById(student_id).get();

        return new StudentDetailResponseDto(student);
    }


    public List<GroupMemberAddRequestDto> getStudentJoinRequest(int group_id) {
        List<Student> tst = studentRepository.findAllByJobGroup_Id(group_id);
        List<GroupMemberAddRequestDto> list = new ArrayList<>();
        for(Student s : tst){
            GroupMemberAddRequestHistory dto = groupMemberAddRequestHistoryRepository.findByStudentId(s.getId());
            if(dto.getAccept().equals("before_confirm")){
                list.add(new GroupMemberAddRequestDto(dto.getId(), "before_confirm", dto.getStudent().getId(), dto.getStudent().getNickname()));
            }
        }
        return list;
    }

    public void addStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudentId(studentId);
        addRequest.updateAccept();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }

    public void updateCertificate(CertificateRequestDto dto) {
        Optional<Certificate> getCertificate = certificateRepository.findById(dto.getCertificateId());
        CertificateIssueRequestHistory updateRequest = certificateIssueRequestHistoryRepository.findByStudentId(dto.getStudentId());
        updateRequest.updateCertificate(getCertificate.get());
        certificateIssueRequestHistoryRepository.save(updateRequest);
    }

    public void rejectStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudentId(studentId);
        addRequest.updateReject();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }
}
