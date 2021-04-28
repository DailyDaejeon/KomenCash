package com.komencash.backend.service;

import com.komencash.backend.dto.law.LawAddReqSelectResponse;
import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.entity.Group;
import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.law.Law;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.LawAddRequestHistoryRepository;
import com.komencash.backend.repository.LawRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LawService {

    @Autowired
    LawRepository lawRepository;

    @Autowired
    LawAddRequestHistoryRepository lawAddRequestHistoryRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;


    public List<LawSelectResponse> findLawByGroupId(int groupId) {
        List<Law> lawList =  lawRepository.findByGroup_Id(groupId).orElseGet(ArrayList::new);

        List<LawSelectResponse> resultList = new ArrayList();
        for(Law law : lawList) {
            resultList.add(new LawSelectResponse(law));
        }

        return resultList;
    }


    public boolean updateLaw(LawInsertUpdateRequest lawInsertUpdateRequest) {
        Law law = lawRepository.findById(lawInsertUpdateRequest.getId()).orElse(null);
        if(law == null) return false;

        Group group = groupRepository.findById(lawInsertUpdateRequest.getGroup_id()).orElse(null);
        law.updateLaw(lawInsertUpdateRequest, group);
        lawRepository.save(law);
        return true;
    }


    // Stream, Map
    public List<LawAddReqSelectResponse> findLawRequestByGroupId(int groupId) {

        List<LawAddReqSelectResponse> resList = new ArrayList();

        List<Student> students = studentRepository.findAllByGroupId(groupId);
        for(Student student : students) {
            List<LawAddRequestHistory> reqList = lawAddRequestHistoryRepository.findByStudent_Id(student.getId());
            for(LawAddRequestHistory request : reqList) {
                resList.add(new LawAddReqSelectResponse(request));
            }
        }

        return resList;
    }
}
