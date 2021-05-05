package com.komencash.backend.controller;

import com.komencash.backend.dto.credit.CreditSelectResponse;
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


    @ApiOperation(value = "신용등급 전체 조회", notes = "그룹 내 모든 그룹원의 신용등급 정보를 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<CreditSelectResponse> findCreditList(@PathVariable("group-id") int groupId){
        return creditService.findCreditList(groupId);
    }
}
