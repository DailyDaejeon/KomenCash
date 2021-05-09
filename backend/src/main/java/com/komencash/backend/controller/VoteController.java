package com.komencash.backend.controller;

import com.komencash.backend.dto.vote.VoteDetailFindResponseDto;
import com.komencash.backend.dto.vote.VoteAddUpdateRequestDto;
import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.service.VoteService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService voteService;


    @ApiOperation(value = "투표 목록 조회", notes = "입력받은 그룹 아이디의 투표 목록 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<VoteFindResponseDto> getVoteList(@PathVariable("group-id") int groupId) {
        return voteService.findVoteListByGroupId(groupId);
    }


    @ApiOperation(value = "투표 생성", notes = "투표 생성에 필요한 정보를 받아서 투표를 생성")
    @PostMapping("")
    public boolean addVote(@RequestBody VoteAddUpdateRequestDto voteAddUpdateRequestDto) {
        return voteService.addVote(voteAddUpdateRequestDto);
    }


    @ApiOperation(value = "투표 상세 조회", notes = "투표 아이디를 받아 해당 투표의 상세 정보를 조회")
    @ApiImplicitParam(name = "vote-id", value = "vote-id(투표 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{vote-id}")
    public VoteDetailFindResponseDto getVoteDetail(@PathVariable("vote-id") int voteId) {
        return voteService.findVoteByVoteId(voteId);
    }


    @ApiOperation(value = "투표 삭제", notes = "투표 아이디를 받아 해당 투표를 삭제")
    @ApiImplicitParam(name = "vote-id", value = "vote-id(투표 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{vote-id}")
    public boolean deleteVote(@PathVariable("vote-id") int voteId) {
        return voteService.deleteVote(voteId);
    }
}
