package com.komencash.backend.service;

import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.student.StudentJoinRequestDto;
import com.komencash.backend.dto.student.StudentLoginRequestDto;
import com.komencash.backend.dto.student.StudentState;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.certificate.Acquisition;
import com.komencash.backend.entity.certificate.Certificate;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UStudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AccountHistoryRepository accountHistoryRepository;
    @Autowired
    GroupMemberAddRequestHistoryRepository groupMemberAddRequestHistoryRepository;
    @Autowired
    AcquisitionRepository acquisitionRepository;
    @Autowired
    JobService jobService;
    public Map<String, Object> joinStudent(StudentJoinRequestDto request) {
        Map<String, Object> resultMap = new HashMap<>();
        Group group = groupRepository.findByCode(request.getCode());
        if(group == null){
            System.out.println("그런 그룹 없습니다.");
            resultMap.put("error", "그룹 코드가 잘 못 입력되었습니다.");
            return resultMap;
        }
        if(studentRepository.findByNicknameAndJob_Group_Id(request.getNickname(), group.getId()) != null){
            System.out.println("닉네임이 중복 되었습니다.");
            resultMap.put("error", "닉네임이 중복 되었습니다.");
            return resultMap;
        }


        List<JobSelectResponse> list = jobService.findJobByGroupId(group.getId());
        Job findBaekSoo = null;
        for(JobSelectResponse j : list){
            if(j.getName().equals("무직")){
                findBaekSoo = new Job(j.getId(), j.getName(), j.getSalary(), j.getRole(), j.getQualification(), j.getPersonnel(), j.getRecruitType(), group);
                break;
            }
        }
        Student student = new Student(request.getNickname(), request.getPassword(), findBaekSoo);
        studentRepository.save(student);
        resultMap.put("success", student);
        return resultMap;
    }

    public void addJoinRequest(Student student) {
        groupMemberAddRequestHistoryRepository.save(new GroupMemberAddRequestHistory(Accept.before_confirm, student));
    }

    public boolean login(StudentLoginRequestDto dto) {
        Student student = studentRepository.findByNickname(dto.getNickName());
        if(student == null) return false;

        if(groupMemberAddRequestHistoryRepository.findByStudentId(student.getId()).getAccept().equals(Accept.accept)) {
            if (dto.getPassword().equals(student.getPassword())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public StudentState getStudentState(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        List<AccountHistory> account = accountHistoryRepository.findAllByStudent_Id(studentId);
        List<String> certificate = acquisitionRepository.findCertificate_NameByStudent_Id(studentId);
        certificate.forEach(s -> {
            System.out.println(certificate);
        });
        int balance = 0;
        if(account.size() != 0){
            balance = account.get(account.size()-1).getBalance();
        }

        String nickname = student.getNickname();
        String jobName = student.getJob().getName();
        int jobSalary = student.getJob().getSalary();

        StudentState state = new StudentState(studentId, nickname, balance, jobName, jobSalary, certificate);

        return state;
    }
}
