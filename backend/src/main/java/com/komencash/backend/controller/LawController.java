package com.komencash.backend.controller;

import com.komencash.backend.dto.law.*;
import com.komencash.backend.dto.request.LawAddReqFindListResponseDto;
import com.komencash.backend.dto.request.LawAddReqFindDetailResponseDto;
import com.komencash.backend.service.LawService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/law")
public class LawController {

    @Autowired
    LawService lawService;


    @ApiOperation(value = "헌법 추가", notes = "입력받은 헌법 정보로 추가하고 결과 반환")
    @PostMapping
    public boolean addLaw(@RequestBody LawAddUpdateRequestDto lawAddUpdateRequestDto) {
        return lawService.addLaw(lawAddUpdateRequestDto);
    }


    @ApiOperation(value = "헌법 목록 조회", notes = "입력받은 그룹 아이디의 헌법 목록 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{group-id}")
    public List<LawFindResponseDto> getLawByGroupId(@PathVariable("group-id") int groupId) {
        return lawService.findLawByGroupId(groupId);
    }


    @ApiOperation(value = "헌법 정보 수정", notes = "입력받은 헌법 정보로 update 후 결과 반환")
    @PutMapping
    public boolean updateLaw(@RequestBody LawAddUpdateRequestDto lawAddUpdateRequestDto) {
        return lawService.updateLaw(lawAddUpdateRequestDto);
    }


    @ApiOperation(value = "헌법 추가 요청 생성", notes = "입력받은 헌법 정보로 헌법 추가 요청을 생성하고 결과 반환")
    @PostMapping("law-add-request")
    public boolean addLawAddReq(@RequestBody LawAddReqAddRequestDto lawAddReqAddRequestDto) {
        return lawService.addLawAddReq(lawAddReqAddRequestDto);
    }


    @ApiOperation(value = "법률 추가/수정 요청 목록 조회", notes = "입력받은 그룹아이디의 미확인된 헌법 추가/수정 요청 목록 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request-list/{group-id}")
    public List<LawAddReqFindListResponseDto> getLawAddReqByGroupId(@PathVariable("group-id") int groupId) {
        return lawService.findLawRequestByGroupId(groupId);
    }


    @ApiOperation(value = "법률 추가/수정 요청 상세 조회", notes = "입력받은 요청 아이디의 상세 정보를 조회")
    @ApiImplicitParam(name = "request-id", value = "request-id(요청 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request/{request-id}")
    public LawAddReqFindDetailResponseDto getLawAddRequestByReqId(@PathVariable("request-id") int requestId) {
        return lawService.findLawRequestByRequestId(requestId);
    }


    @ApiOperation(value = "법률 추가/수정 요청 승인/거절", notes = "입력받은 법률 추가/요청 처리 정보로 update후 결과 반환")
    @PutMapping("/add-request/accept")
    public boolean updateLawAddRequestAccept(@RequestBody LawAddReqAcceptUpdateRequestDto lawAddReqAcceptUpdateRequestDto) {
        return lawService.updateLawRequestAccept(lawAddReqAcceptUpdateRequestDto);
    }
}
