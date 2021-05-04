package com.komencash.backend.controller;

import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.tax.TaxDetailResponse;
import com.komencash.backend.service.TaxService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    TaxService taxService;

    @ApiOperation(value = "세금 상세 조회", notes = "입력받은 group-id로 그룹 내의 세금 내역을 상세하게 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{group-id}")
    public TaxDetailResponse findTaxDetail(@PathVariable("group-id") int groupId){
        return taxService.findTaxDetail(groupId);
    }
}
