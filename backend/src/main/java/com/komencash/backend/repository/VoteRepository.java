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
                                                      "group by choice_item_num) va " +
                   "on vi.vote_id = va.vote_id and vi.item_num = va.choice_item_num " +
                   "where vi.vote_id = :voteId", nativeQuery = true)
    List<VoteItemResultInterface> findCntByVote_Id(@Param("voteId") int voteId);

    List<Vote> findByStudent_Job_Group_Id(int groupId);
}