package com.komencash.backend.controller;

import com.komencash.backend.dto.credit.CreditFindGradeAndPointResponseDto;
import com.komencash.backend.dto.credit.CreditFindResponseDto;
import com.komencash.backend.service.CreditService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    CreditService creditService;


    @ApiOperation(value = "학생 신용등급 목록 조회", notes = "입력받은 그룹의 모든 학생의 신용등급 목록 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<CreditFindResponseDto> getCreditList(@PathVariable("group-id") int groupId){
        return creditService.findCreditList(groupId);
    }


    @ApiOperation(value = "신용등급 조회", notes = "입력받은 학생 아이디의 신용 등급 정보를 반환")
    @ApiImplicitParam(name = "student-id", value = "student-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/student/{student-id}")
    public CreditFindGradeAndPointResponseDto getCreditGrade(@PathVariable("student-id") int studentId){
        return creditService.findCreditGrade(studentId);
    }
}
