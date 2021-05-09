package com.komencash.backend.repository;

import com.komencash.backend.entity.vote.VoteAttend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteAttendRepository extends JpaRepository<VoteAttend, Integer> {

    List<VoteAttend> findByVote_Id(int voteId);
}