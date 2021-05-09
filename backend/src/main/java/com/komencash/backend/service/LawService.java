package com.komencash.backend.service;

import com.komencash.backend.dto.law.LawAddReqAcceptUpdateRequestDto;
import com.komencash.backend.dto.request.LawAddReqFindListResponseDto;
import com.komencash.backend.dto.request.LawAddReqFindDetailResponseDto;
import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.dto.law.LawFindResponseDto;
import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
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


    public List<LawFindResponseDto> findLawByGroupId(int groupId) {
        List<LawFindResponseDto> resultList = new ArrayList();

        List<Law> laws =  lawRepository.findByGroup_Id(groupId);
        laws.forEach(law -> resultList.add(new LawFindResponseDto(law)));

        return resultList;
    }


    public boolean updateLaw(LawAddUpdateRequestDto lawAddUpdateRequestDto) {
        Law law = lawRepository.findById(lawAddUpdateRequestDto.getId()).orElse(null);
        if(law == null) return false;

        Group group = groupRepository.findById(lawAddUpdateRequestDto.getGroup_id()).orElse(null);
        law.updateLaw(lawAddUpdateRequestDto, group);
        lawRepository.save(law);
        return true;
    }


    public List<LawAddReqFindListResponseDto> findLawRequestByGroupId(int groupId) {

        List<LawAddReqFindListResponseDto> lawAddReqFindListResponseDtos = new ArrayList();

        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);
        students.forEach(student -> {
            List<LawAddRequestHistory> lawAddRequestHistories = lawAddRequestHistoryRepository.findByStudent_Id(student.getId());
            lawAddRequestHistories.forEach(lawAddRequestHistory -> {
                if (lawAddRequestHistory.getAccpet().equals(Accept.before_confirm))
                    lawAddReqFindListResponseDtos.add(new LawAddReqFindListResponseDto(lawAddRequestHistory));
            });
        });

        return lawAddReqFindListResponseDtos;
    }


    public LawAddReqFindDetailResponseDto findLawRequestByRequestId(int requestId) {
        LawAddRequestHistory lawAddRequestHistory = lawAddRequestHistoryRepository.findById(requestId).orElse(null);
        VoteFindResponseDto voteFindResponseDto = voteService.findCntByVote_Id(lawAddRequestHistory.getVote().getId());

        return new LawAddReqFindDetailResponseDto(lawAddRequestHistory, voteFindResponseDto);
    }


    public boolean updateLawRequestAccept(LawAddReqAcceptUpdateRequestDto lawAddReqAcceptUpdateRequestDto){
        LawAddRequestHistory lawAddRequestHistory = lawAddRequestHistoryRepository.findById(lawAddReqAcceptUpdateRequestDto.getId()).orElse(null);
        if(lawAddRequestHistory == null) return false;

        if(lawAddRequestHistory.getAccpet().equals(Accept.before_confirm)) lawAddRequestHistory.updateLawRequestAccept(lawAddReqAcceptUpdateRequestDto.getAccept());
        lawAddRequestHistoryRepository.save(lawAddRequestHistory);
        return true;
    }
}
