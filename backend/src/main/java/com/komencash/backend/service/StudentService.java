package com.komencash.backend.service;

import com.komencash.backend.dto.Request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
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
    public StudentResponseDto getStudent(int group_id){
        List<Student> student = studentRepository.findAllByGroupId(group_id);
        return new StudentResponseDto(student);
    }

    public StudentDetailResponseDto getStudentDetail(int student_id) {
        Student student = studentRepository.findById(student_id).get();

        return new StudentDetailResponseDto(student);
    }


    public List<GroupMemberAddRequestDto> getStudentJoinRequest(int group_id) {
        List<Student> tst = studentRepository.findAllByGroupId(group_id);
        List<GroupMemberAddRequestDto> list = new ArrayList<>();
        for(Student s : tst){
            GroupMemberAddRequestHistory dto = groupMemberAddRequestHistoryRepository.findByStudentId(s.getId());
            if(dto.getAccept().equals("before_confirm")){
                list.add(new GroupMemberAddRequestDto(dto.getId(), "before_confirm", dto.getStudent().getId(), dto.getStudent().getNickname()));
            }
        }
        return list;
    }
}
