package com.komencash.backend.service;

import com.komencash.backend.dto.request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.student.StudentJoinRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.GroupMemberAddRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.GroupMemberAddRequestHistoryRepository;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UStudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMemberAddRequestHistoryRepository groupMemberAddRequestHistoryRepository;

    public Student joinStudent(StudentJoinRequestDto request) {
        Group group = groupRepository.findByCode(request.getCode());
        if(group == null){
            System.out.println("그런 그룹 없습니다.");
            return null;
        }
        Student student = new Student(request.getNickname(), request.getPassword());
        return studentRepository.save(student);
    }

    public void addJoinRequest(Student student) {
        groupMemberAddRequestHistoryRepository.save(new GroupMemberAddRequestHistory(Accept.before_confirm, student));
    }

}
