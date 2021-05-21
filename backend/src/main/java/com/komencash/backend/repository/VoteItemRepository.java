package com.komencash.backend.repository;

import com.komencash.backend.entity.vote.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteItemRepository extends JpaRepository<VoteItem, Integer> {
    VoteItem findByItemNumAndVote_Id(int itemNum, int voteId);
}
