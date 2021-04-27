package com.komencash.backend.repository;

import com.komencash.backend.entity.Group;
import com.komencash.backend.entity.vote.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteItemRepository extends JpaRepository<VoteItem, Integer> {
}
