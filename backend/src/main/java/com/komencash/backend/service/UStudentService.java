package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateDetailSelectResponse;
import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.dto.job.JobFindResponseDto;
import com.komencash.backend.dto.student.*;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.entity.request_history.ResumeRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    JobService jobService;
    @Autowired
    CertificateService certificateService;
    @Autowired
    JobAddRequestHistoryRepository jobAddRequestHistoryRepository;
    @Autowired
    ResumeRequestHistoryRepository resumeRequestHistoryRepository;
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


        List<JobFindResponseDto> list = jobService.findJobListByGroupId(group.getId());
        Job findBaekSoo = null;
        for(JobFindResponseDto j : list){
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

    public Map<String,Object> login(StudentLoginRequestDto dto) {
        Map<String, Object> resultMap = new HashMap<>();
        Student student = studentRepository.findByNickname(dto.getNickname());
        if(student == null){
            resultMap.put("fail", "존재하는 닉네임이 아닙니다.");
            return resultMap;
        }
        GroupMemberAddRequestHistory test = groupMemberAddRequestHistoryRepository.findByStudent_Id(student.getId());
        System.out.println(test);
        Accept accept = test.getAccept();
        if(accept!= null && accept.equals(Accept.accept)) {
            if (dto.getPassword().equals(student.getPassword())) {
                resultMap.put("success", student);
                return resultMap;
            }
            resultMap.put("fail", "비밀번호가 틀립니다.");
            return resultMap;
        }else
            resultMap.put("fail", "그룹에서 수락하지 않았습니다");
        return resultMap;
    }

    public StudentState getStudentState(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        List<AccountHistory> account = accountHistoryRepository.findByStudent_Id(studentId);
        List<CertificateDetailSelectResponse> certificates = certificateService.findCertificateListByStudent(studentId);
        certificates.forEach(s -> {
            System.out.println(s);
        });
        int balance = 0;
        if(account.size() != 0){
            balance = account.get(account.size()-1).getBalance();
        }

        String nickname = student.getNickname();
        String jobName = student.getJob().getName();
        int jobSalary = student.getJob().getSalary();

        StudentState state = new StudentState(studentId, nickname, balance, jobName, jobSalary, certificates);

        return state;
    }

    public boolean addJobRequestWithResume(StudentJobRequestDto dto) {
        Job job = jobService.findJobById(dto.getJobId());
        Student student = studentRepository.findById(dto.getStudentId()).orElse(null);
        ResumeRequestHistory resumeRequestHistory =
                new ResumeRequestHistory(dto.getTitle(),
                dto.getContent(),
                Accept.before_confirm,
                job,
                student
        );

        resumeRequestHistoryRepository.save(resumeRequestHistory);
        return true;
    }

    public boolean updateStudentPassword(StudentUpdatePasswordRequestDto studentUpdatePasswordRequestDto) {
        Student student = studentRepository.findById(studentUpdatePasswordRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        student.updatePassword(studentUpdatePasswordRequestDto.getPassword());
        studentRepository.save(student);
        return true;
    }
}
