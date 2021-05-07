package com.komencash.backend.service;

import com.komencash.backend.dto.group.GroupAddModifyRequestDto;
import com.komencash.backend.dto.group.GroupFindResponseDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.teacher.Teacher;
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


    public int addGroup(GroupAddModifyRequestDto groupAddModifyRequestDto){
        Teacher teacher = teacherRepository.findById(groupAddModifyRequestDto.getTeacherId()).orElse(null);
        if(teacher == null) return 0;

        Group group = new Group(groupAddModifyRequestDto, teacher);
        group = groupRepository.save(group);

        return group.getId();
    }


    public List<GroupFindResponseDto> findGroup(int teacherId){
        List<GroupFindResponseDto> groupFindResponseDtos = new ArrayList<>();

        List<Group> groups = groupRepository.findByTeacher_Id(teacherId);
        groups.forEach(group -> {
            int studentsSize = studentRepository.countByJob_Group_Id(group.getId());
            groupFindResponseDtos.add(new GroupFindResponseDto(group, studentsSize));
        });

        return groupFindResponseDtos;
    }


    public boolean modifyGroup(GroupAddModifyRequestDto groupAddModifyRequestDto) {
        Group group = groupRepository.findById(groupAddModifyRequestDto.getId()).orElse(null);
        if(group == null) return false;

        group.updateGroup(groupAddModifyRequestDto);
        groupRepository.save(group);
        return true;
    }


    public boolean removeGroup(int groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return false;

        groupRepository.delete(group);
        return true;
    }
}
