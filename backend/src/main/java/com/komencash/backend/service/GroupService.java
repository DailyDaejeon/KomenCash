package com.komencash.backend.service;

import com.komencash.backend.dto.group.GroupInsertUpdateRequest;
import com.komencash.backend.dto.group.GroupResponseDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
import com.komencash.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;
    public int saveGroup(GroupInsertUpdateRequest groupInsertUpdateRequest){
        Group savedGroup = new Group(groupInsertUpdateRequest, teacherRepository.findById(groupInsertUpdateRequest.getTeacherId()).get());
//        savedGroup.setTeacher(teacherRepository.findById(groupInsertUpdateRequest.getTeacher_id()).get());
        Group group = groupRepository.save(savedGroup);
        int groupId = group.getId();

        return groupId;
    }

    public List<GroupResponseDto> getGroup(int id){       // 선생님 id를 가지고 group 리스트 보여주기
        List<Group> group = groupRepository.findByTeacher_Id(id);

        List<GroupResponseDto> result = new ArrayList<>();
        group.forEach(s -> {
            List<Student> students = studentRepository.findAllByJob_Group_Id(s.getId());
            GroupResponseDto dto = new GroupResponseDto(s, students.size());
            result.add(dto);
        });
        return result;
    }

    public void updateGroup(GroupInsertUpdateRequest groupInsertUpdateRequest) {
        Group group = groupRepository.findById(groupInsertUpdateRequest.getId()).orElse(null);
        group.updateGroup(groupInsertUpdateRequest.getName(), groupInsertUpdateRequest.getMonetaryUnitName(),
                groupInsertUpdateRequest.getTaxRate(), groupInsertUpdateRequest.getInflationRate());
        groupRepository.save(group);
    }

    public void deleteGroup(int group_id) {
        groupRepository.deleteById(group_id);
    }
}
