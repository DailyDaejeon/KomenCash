package com.komencash.backend.controller;

import com.komencash.backend.dto.request.CaseAcceptRequest;
import com.komencash.backend.dto.request.CaseSelectResponse;
import com.komencash.backend.dto.store.StoreItemInsertUpdateRequest;
import com.komencash.backend.service.CaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "사건 경위서 상세 조회", notes = "입력받은 case-id의 사건 경위서를 조회")
    @ApiImplicitParam(name = "case-id", value = "case-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{case-id}")
    public CaseSelectResponse selectCase(@PathVariable("case-id")int caseId){
        return caseService.selectCase(caseId);
    }



    @ApiOperation(value = "학생별 전과 조회", notes = "입력받은 student-id의 모든 사건 경위서 목록을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/student/{student-id}")
    public List<CaseSelectResponse> selectCaseListByStudent(@PathVariable("student-id")int studentId){
        return caseService.selectCaseListByStudent(studentId);
    }


    @ApiOperation(value = "사건 처리", notes = "사건 처리 방식을 받아서 처리 결과를 반환")
    @PutMapping("/accept")
    public boolean updateCaseAccept(@RequestBody CaseAcceptRequest caseAcceptRequest) {
        return caseService.updateCaseAccept(caseAcceptRequest);
    }
}
