package com.komencash.backend.service;

import com.komencash.backend.dto.vote.*;
import com.komencash.backend.entity.Student;
import com.komencash.backend.entity.vote.Vote;
import com.komencash.backend.entity.vote.VoteItem;
import com.komencash.backend.repository.StudentRepository;
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
    StudentRepository studentRepository;

    public VoteResultResponse findCntByVote_Id(int voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        List<VoteItemResultResponse> voteItemResultResponses = new ArrayList<>();

        List<VoteItemResultInterface> voteItemResultInterfaces = voteRepository.findCntByVote_Id(voteId);
        for(VoteItemResultInterface response : voteItemResultInterfaces) {
            VoteItemResultResponse voteItemResultResponse = new VoteItemResultResponse(response);
            voteItemResultResponses.add(voteItemResultResponse);
        }

        return new VoteResultResponse(vote, voteItemResultResponses);
    }

    public List<VoteResultResponse> findByGroupId(int groupId){
        List<VoteResultResponse> voteResultResponses = new ArrayList<>();

        List<Vote> voteList = voteRepository.findByStudent_Job_Group_Id(groupId);
        for(Vote vote : voteList) {
            System.out.println(vote.getId());
            voteResultResponses.add(findCntByVote_Id(vote.getId()));
        }

        return voteResultResponses;
    }

    public boolean saveVote(VoteInsertUpdateRequest voteInsertUpdateRequest) {

        Student student = studentRepository.findById(voteInsertUpdateRequest.getStudentId()).orElse(null);
        Vote vote = new Vote(voteInsertUpdateRequest, student);
        voteRepository.save(vote);

        List<VoteItemInsertUpdateRequest> voteItems = voteInsertUpdateRequest.getVoteItemList();
        for(VoteItemInsertUpdateRequest voteItemInsertUpdateRequest : voteItems) {
            VoteItem voteItem = new VoteItem(voteItemInsertUpdateRequest, vote);
            voteItemRepository.save(voteItem);
        }

        return true;
    }
}
