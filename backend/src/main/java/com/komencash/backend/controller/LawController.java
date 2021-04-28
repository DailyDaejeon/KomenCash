package com.komencash.backend.controller;

import com.komencash.backend.dto.Request.LawAddReqSelectListResponse;
import com.komencash.backend.dto.Request.LawAddReqSelectResponse;
import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.dto.vote.VoteResultResponse;
import com.komencash.backend.service.LawService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/law")
public class LawController {

    @Autowired
    LawService lawService;

    @ApiOperation(value = "헌법 리스트 조회", notes = "그룹 아이디를 받아 해당 그룹의 헌법 리스트를 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{group-id}")
    public List<LawSelectResponse> findLawByGroupId(@PathVariable("group-id") int groupId) {
        return lawService.findLawByGroupId(groupId);
    }


    @ApiOperation(value = "헌법 정보 수정", notes = "헌법 정보를 입력받아 해당 헌법 아이디에서 바뀐 부분을 수정")
    @PutMapping("")
    public boolean updateLaw(@RequestBody LawInsertUpdateRequest lawInsertUpdateRequest) {
        return lawService.updateLaw(lawInsertUpdateRequest);
    }

    @ApiOperation(value = "법률 추가/수정 요청 리스트 조회", notes = "그룹 아이디를 받아 해당 그룹의 헌법 추가/수정 요청 리스트를 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add_request_list/{group-id}")
    public List<LawAddReqSelectListResponse> findLawRequestByGroupId(@PathVariable("group-id") int groupId) {
        return lawService.findLawRequestByGroupId(groupId);
    }


    @ApiOperation(value = "법률 추가/수정 요청 상세 조회", notes = "요청 아이디를 받아 해당 요청의 상세 정보를 조회")
    @ApiImplicitParam(name = "request-id", value = "request-id(요청 아이디)", dataType = "int", required = true)
    @GetMapping("/add_request/{request-id}")
    public LawAddReqSelectResponse findLawRequestByReqId(@PathVariable("request-id") int requestId) {
        return lawService.findLawRequestByReqId(requestId);
    }



}
