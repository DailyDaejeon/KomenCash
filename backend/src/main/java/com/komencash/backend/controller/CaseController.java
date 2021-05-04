package com.komencash.backend.controller;

import com.komencash.backend.dto.request.CaseSelectResponse;
import com.komencash.backend.service.CaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    CaseService caseService;

    @ApiOperation(value = "사건 경위서 목록 조회", notes = "입력받은 group-id의 group 내 모든 사건 경위서 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<CaseSelectResponse> selectCaseList(@PathVariable("group-id")int groupId){
        return caseService.selectCaseList(groupId);
    }
}
