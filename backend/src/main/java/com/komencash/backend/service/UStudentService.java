package com.komencash.backend.service;

import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.student.StudentJoinRequestDto;
import com.komencash.backend.dto.student.StudentLoginRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.GroupMemberAddRequestHistoryRepository;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UStudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMemberAddRequestHistoryRepository groupMemberAddRequestHistoryRepository;

    @Autowired
    JobService jobService;
    public Student joinStudent(StudentJoinRequestDto request) {
        Group group = groupRepository.findByCode(request.getCode());
        if(group == null){
            System.out.println("그런 그룹 없습니다.");
            return null;
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
        return studentRepository.save(student);
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

}
