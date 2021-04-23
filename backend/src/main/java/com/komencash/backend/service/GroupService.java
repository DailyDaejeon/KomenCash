package com.komencash.backend.service;

import com.komencash.backend.dto.GroupInsertUpdateRequest;
import com.komencash.backend.dto.GroupResponseDto;
import com.komencash.backend.entity.Group;
import com.komencash.backend.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {
    @Autowired
    private final GroupRepository groupRepository;

    public boolean saveGroup(GroupInsertUpdateRequest groupInsertUpdateRequest){
        groupRepository.save(new Group(groupInsertUpdateRequest));
        return true;
    }

    public GroupResponseDto getGroup(int id){       // 선생님 id를 가지고 group 리스트 보여주기
        Group group = groupRepository.findByTeacherId(id).get();
        return new GroupResponseDto((List<Group>) group);
    }
}
