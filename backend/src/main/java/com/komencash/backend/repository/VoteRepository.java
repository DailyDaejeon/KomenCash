package com.komencash.backend.repository;

import com.komencash.backend.dto.vote.VoteItemResultInterface;
import com.komencash.backend.dto.vote.VoteItemResultResponse;
import com.komencash.backend.dto.vote.VoteResultCnt;
import com.komencash.backend.entity.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(value = "select vi.id as id, vi.item_num as  itemNum, vi.content as content, if(va.cnt is null, 0, va.cnt) as resultCnt " +
            "from vote_item vi left outer join (select vote_id, choice_item_num, count(*) as cnt " +
            "from vote_attend " +
            "where vote_id = :voteId " +
            "group by choice_item_num) va " +
            "on vi.vote_id = va.vote_id and vi.item_num = va.choice_item_num", nativeQuery = true)
    List<VoteItemResultInterface> findCntByVote_Id(@Param("voteId") int voteId);
}