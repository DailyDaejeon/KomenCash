package com.komencash.backend.service;

import com.komencash.backend.dto.group.GroupInsertUpdateRequest;
import com.komencash.backend.dto.group.GroupResponseDto;
import com.komencash.backend.entity.Group;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    GroupRepository groupRepository;

    public boolean saveGroup(GroupInsertUpdateRequest groupInsertUpdateRequest){
        Group savedGroup = new Group(groupInsertUpdateRequest, teacherRepository.findById(groupInsertUpdateRequest.getTeacher_id()).get());
//        savedGroup.setTeacher(teacherRepository.findById(groupInsertUpdateRequest.getTeacher_id()).get());
        groupRepository.save(savedGroup);
        return true;
    }

    public GroupResponseDto getGroup(int id){       // 선생님 id를 가지고 group 리스트 보여주기
        List<Group> group = groupRepository.findByTeacherId(id);
        return new GroupResponseDto(group);
    }

    public void updateGroup(GroupInsertUpdateRequest groupInsertUpdateRequest) {
        Group group = groupRepository.findById(groupInsertUpdateRequest.getId()).orElse(null);
        group.updateGroup(groupInsertUpdateRequest.getName(), groupInsertUpdateRequest.getMonetary_unit_name(), groupInsertUpdateRequest.getTax_rate());
        groupRepository.save(group);
    }

    public void deleteGroup(int group_id) {
        groupRepository.deleteById(group_id);
    }
}
