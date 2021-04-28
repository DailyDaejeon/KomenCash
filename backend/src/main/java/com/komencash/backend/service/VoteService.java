package com.komencash.backend.service;

import com.komencash.backend.dto.vote.VoteItemResultInterface;
import com.komencash.backend.dto.vote.VoteItemResultResponse;
import com.komencash.backend.dto.vote.VoteResultResponse;
import com.komencash.backend.entity.vote.Vote;
import com.komencash.backend.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    public VoteResultResponse findCntByVote_Id(int voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);

        List<VoteItemResultInterface> voteItemResultInterfaces = voteRepository.findCntByVote_Id(voteId);
        List<VoteItemResultResponse> voteItemResultResponses = new ArrayList<>();
        for(VoteItemResultInterface response : voteItemResultInterfaces) {
            VoteItemResultResponse voteItemResultResponse = new VoteItemResultResponse(response.getId(), response.getItemNum(), response.getContent(), response.getResultCnt());
            voteItemResultResponses.add(voteItemResultResponse);
        }

        return new VoteResultResponse(vote, voteItemResultResponses);
    }
}
