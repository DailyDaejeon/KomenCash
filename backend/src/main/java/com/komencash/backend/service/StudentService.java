package com.komencash.backend.service;

import com.komencash.backend.dto.Request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.certificate.CertificateRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.student.Student;
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

    public List<StudentResponseDto> getStudent(int group_id){
        System.out.println(studentRepository.findById(2).get().getNickname());
        List<Student> student = studentRepository.findAllByJob_Group_Id(group_id);
        List<StudentResponseDto> students = new ArrayList<>();
        student.forEach(s ->{
            StudentResponseDto dto = new StudentResponseDto(s.getNickname(), s.getJob());
            students.add(dto);
            System.out.println(dto);
        });
        return students;
    }

    public StudentDetailResponseDto getStudentDetail(int student_id) {
        Student student = studentRepository.findById(student_id).get();

        return new StudentDetailResponseDto(student);
    }


    public List<GroupMemberAddRequestDto> getStudentJoinRequest(int group_id) {
        List<Student> tst = studentRepository.findAllByJob_Group_Id(group_id);
        List<GroupMemberAddRequestDto> list = new ArrayList<>();
        tst.forEach(s ->{
            GroupMemberAddRequestHistory dto = groupMemberAddRequestHistoryRepository.findByStudentId(s.getId());
            System.out.println(dto.getStudent().getNickname()+" , "+dto.getAccept());

            if(dto.getAccept()==Accept.before_confirm){
                list.add(new GroupMemberAddRequestDto(dto.getId(), dto.getAccept().toString(), dto.getStudent().getId(), dto.getStudent().getNickname()));
            }
        });


        return list;
    }

    public void addStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudentId(studentId);
        addRequest.updateAccept();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }
    public void rejectStudent(int studentId) {
        GroupMemberAddRequestHistory addRequest = groupMemberAddRequestHistoryRepository.findByStudentId(studentId);
        addRequest.updateReject();
        groupMemberAddRequestHistoryRepository.save(addRequest);
    }


    public void updateCertificate(CertificateRequestDto dto) {
        Optional<Certificate> getCertificate = certificateRepository.findById(dto.getCertificateId());
        CertificateIssueRequestHistory updateRequest = certificateIssueRequestHistoryRepository.findByStudentId(dto.getStudentId());
        updateRequest.updateCertificate(getCertificate.get());
        certificateIssueRequestHistoryRepository.save(updateRequest);
    }

    public void resetPw(int studentId) {
        Optional<Student> stu = studentRepository.findById(studentId);
        stu.get().updatePw();
        studentRepository.save(stu.get());
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }
}
