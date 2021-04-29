package com.komencash.backend.service;

import com.komencash.backend.dto.Request.LawAddReqSelectListResponse;
import com.komencash.backend.dto.Request.LawAddReqSelectResponse;
import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.dto.vote.VoteResultResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.law.Law;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import com.komencash.backend.repository.*;
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
    VoteRepository voteRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    VoteService voteService;

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


    public List<LawAddReqSelectListResponse> findLawRequestByGroupId(int groupId) {

        List<LawAddReqSelectListResponse> resList = new ArrayList();

        List<Student> students = studentRepository.findAllByJobGroup_Id(groupId);
        for(Student student : students) {
            List<LawAddRequestHistory> reqList = lawAddRequestHistoryRepository.findByStudent_Id(student.getId());
            for(LawAddRequestHistory request : reqList) {
                resList.add(new LawAddReqSelectListResponse(request));
            }
        }
        return resList;
    }


    public LawAddReqSelectResponse findLawRequestByReqId(int requestId) {
        LawAddRequestHistory request = lawAddRequestHistoryRepository.findById(requestId).orElse(null);
        VoteResultResponse result = voteService.findCntByVote_Id(request.getVote().getId());

        return new LawAddReqSelectResponse(request, result);
    }
}
