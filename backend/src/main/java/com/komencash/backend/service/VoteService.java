package com.komencash.backend.service;

import com.komencash.backend.dto.vote.*;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.vote.Vote;
import com.komencash.backend.entity.vote.VoteAttend;
import com.komencash.backend.entity.vote.VoteItem;
import com.komencash.backend.repository.StudentRepository;
import com.komencash.backend.repository.VoteAttendRepository;
import com.komencash.backend.repository.VoteItemRepository;
import com.komencash.backend.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteItemRepository voteItemRepository;

    @Autowired
    VoteAttendRepository voteAttendRepository;

    @Autowired
    StudentRepository studentRepository;


    public VoteFindResponseDto findCntByVote_Id(int voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        List<VoteItemFindResponseDto> voteItemResultResponses = new ArrayList<>();

        List<VoteItemFindInterface> voteItemFindInterfaces = voteRepository.findCntByVote_Id(voteId);
        voteItemFindInterfaces.forEach(voteItemFindInterface ->
                voteItemResultResponses.add(new VoteItemFindResponseDto(voteItemFindInterface)));

        return new VoteFindResponseDto(vote, voteItemResultResponses);
    }


    public List<VoteFindResponseDto> findVoteListByGroupId(int groupId){
        List<VoteFindResponseDto> voteFindResponsDtos = new ArrayList<>();

        List<Vote> votes = voteRepository.findByStudent_Job_Group_Id(groupId);
        votes.forEach(vote -> voteFindResponsDtos.add(findCntByVote_Id(vote.getId())));

        return voteFindResponsDtos;
    }


    public boolean addVote(VoteAddUpdateRequestDto voteAddUpdateRequestDto) {

        Student student = studentRepository.findById(voteAddUpdateRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        Vote vote = new Vote(voteAddUpdateRequestDto, student);
        voteRepository.save(vote);

        List<VoteItemAddUpdateRequestDto> voteItemAddUpdateRequestDtos = voteAddUpdateRequestDto.getVoteItemList();
        voteItemAddUpdateRequestDtos.forEach(voteItemAddUpdateRequestDto ->
                        voteItemRepository.save(new VoteItem(voteItemAddUpdateRequestDto, vote)));
        return true;
    }


    public boolean addVoteAttend(VoteAttendAddRequestDto voteAttendAddRequestDto){
        Vote vote = voteRepository.findById(voteAttendAddRequestDto.getVoteId()).orElse(null);
        if(vote == null) return false;

        Student student = studentRepository.findById(voteAttendAddRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        voteAttendRepository.save(new VoteAttend(voteAttendAddRequestDto.getVoteItemNum(), vote, student));
        return true;
    }


    public VoteDetailFindResponseDto findVoteByVoteId(int voteId){

        VoteFindResponseDto voteFindResponseDto = findCntByVote_Id(voteId);

        List<VoteAttendFindResponseDto> voteAttendFindResponsDtos = new ArrayList<>();
        List<VoteAttend> voteAttends = voteAttendRepository.findByVote_Id(voteId);
        voteAttends.forEach(voteAttend -> {
            String content = voteItemRepository.findByItemNumAndVote_Id(voteAttend.getChoiceItemNum(), voteAttend.getVote().getId()).getContent();
            voteAttendFindResponsDtos.add(new VoteAttendFindResponseDto(voteAttend, content));
        });

        VoteDetailFindResponseDto voteDetailFindResponseDto = new VoteDetailFindResponseDto(voteFindResponseDto, voteAttendFindResponsDtos);
        return voteDetailFindResponseDto;
    }


    public boolean deleteVote(int voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if(vote == null) return false;

        voteRepository.delete(vote);
        return true;
    }
}
