package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.dto.teacher.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.vote.VoteInsertUpdateRequest;
import com.komencash.backend.dto.vote.VoteResultResponse;
import com.komencash.backend.service.VoteService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService voteService;

    @ApiOperation(value = "투표 리스트 조회", notes = "그룹 아이디를 받아 해당 그룹의 투표 리스트를 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<VoteResultResponse> findLawByGroupId(@PathVariable("group-id") int groupId) {
        return voteService.findByGroupId(groupId);
    }

    @ApiOperation(value = "투표 생성", notes = "투표 생성에 필요한 정보를 받아서 투표를 생성")
    @PostMapping("")
    public boolean saveTeacher(@RequestBody VoteInsertUpdateRequest voteInsertUpdateRequest) {
        return voteService.saveVote(voteInsertUpdateRequest);
    }
}
