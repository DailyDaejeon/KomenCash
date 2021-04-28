package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawAddReqSelectResponse;
import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.dto.law.LawSelectResponse;
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
    @ApiImplicitParam(name = "group_id", value = "group_id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{group_id}")
    public List<LawSelectResponse> findLawByGroupId(@PathVariable("group_id") int group_id) {
        return lawService.findLawByGroupId(group_id);
    }


    @ApiOperation(value = "헌법 정보 수정", notes = "헌법 정보를 입력받아 해당 헌법 아이디에서 바뀐 부분을 수정")
    @PutMapping("")
    public boolean updateLaw(@RequestBody LawInsertUpdateRequest lawInsertUpdateRequest) {
        return lawService.updateLaw(lawInsertUpdateRequest);
    }

    @ApiOperation(value = "법률 추가/수정 요청 리스트 조회", notes = "그룹 아이디를 받아 해당 그룹의 헌법 추가/수정 요청 리스트를 조회")
    @ApiImplicitParam(name = "group_id", value = "group_id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add_request/{group_id}")
    public List<LawAddReqSelectResponse> findLawRequestByGroupId(@PathVariable("group_id") int group_id) {
        return lawService.findLawRequestByGroupId(group_id);
    }



}
