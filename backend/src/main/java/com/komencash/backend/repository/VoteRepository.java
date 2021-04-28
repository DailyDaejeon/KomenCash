package com.komencash.backend.repository;

import com.komencash.backend.entity.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}