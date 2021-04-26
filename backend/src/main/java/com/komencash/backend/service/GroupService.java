package com.komencash.backend.service;

import com.komencash.backend.dto.GroupInsertUpdateRequest;
import com.komencash.backend.dto.GroupResponseDto;
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
        Group savedGroup = new Group(groupInsertUpdateRequest);
        savedGroup.setTeacher(teacherRepository.findById(groupInsertUpdateRequest.getTeacher_id()).get());
        groupRepository.save(savedGroup);
        return true;
    }

    public GroupResponseDto getGroup(int id){       // 선생님 id를 가지고 group 리스트 보여주기
        Group group = groupRepository.findByTeacherId(id).get();
        return new GroupResponseDto((List<Group>) group);
    }
}
