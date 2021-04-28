package com.komencash.backend.repository;

import com.komencash.backend.entity.vote.VoteAttend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteAttendRepository extends JpaRepository<VoteAttend, Integer> {
}