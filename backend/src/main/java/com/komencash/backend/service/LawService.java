package com.komencash.backend.service;

import com.komencash.backend.dto.law.*;
import com.komencash.backend.dto.request.LawAddReqFindListResponseDto;
import com.komencash.backend.dto.request.LawAddReqFindDetailResponseDto;
import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.law.Law;
import com.komencash.backend.entity.request_history.LawAddRequestHistory;
import com.komencash.backend.entity.vote.Vote;
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



    public boolean addLaw(LawAddUpdateRequestDto lawAddUpdateRequestDto) {
        Group group = groupRepository.findById(lawAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Law law = new Law(lawAddUpdateRequestDto, group);
        lawRepository.save(law);
        return true;
    }


    public List<LawFindResponseDto> findLawByGroupId(int groupId) {
        List<LawFindResponseDto> lawFindResponseDtos = new ArrayList<>();

        List<Law> lawGroupList = lawRepository.findByGroup_IdGroupByLawType(groupId);
        lawGroupList.forEach(lawGroup ->{
            List<LawFindByLawTypeResponseDto> lawFindByLawTypeResponseDtoList = new ArrayList<>();
            String lawType = lawGroup.getLawType();
            List<Law> laws = lawRepository.findByLawType(lawType);
            laws.forEach(law -> lawFindByLawTypeResponseDtoList.add(new LawFindByLawTypeResponseDto(law)));

            lawFindResponseDtos.add(new LawFindResponseDto(lawFindByLawTypeResponseDtoList));
        });

        return lawFindResponseDtos;
    }


    public boolean addLawAddReq(LawAddReqAddRequestDto lawAddReqAddRequestDto) {
        Vote vote = voteRepository.findById(lawAddReqAddRequestDto.getVoteId()).orElse(null);
        if(vote == null) return false;

        Student student = studentRepository.findById(lawAddReqAddRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        LawAddRequestHistory lawAddRequestHistory = new LawAddRequestHistory(lawAddReqAddRequestDto, vote, student);
        lawAddRequestHistoryRepository.save(lawAddRequestHistory);
        return true;
    }


    public boolean updateLaw(LawAddUpdateRequestDto lawAddUpdateRequestDto) {
        Group group = groupRepository.findById(lawAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Law law = lawRepository.findById(lawAddUpdateRequestDto.getId()).orElse(null);
        if(law == null) return false;

        law.updateLaw(lawAddUpdateRequestDto, group);
        lawRepository.save(law);
        return true;
    }


    public List<LawAddReqFindListResponseDto> findLawRequestByGroupId(int groupId) {

        List<LawAddReqFindListResponseDto> lawAddReqFindListResponseDtos = new ArrayList();

        List<Student> students = studentRepository.findByJob_Group_Id(groupId);
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
