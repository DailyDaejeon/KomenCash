package com.komencash.backend.controller;

import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.tax.TaxDetailResponse;
import com.komencash.backend.dto.tax.TaxHistoryInsertUpdateRequest;
import com.komencash.backend.service.TaxService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "세금 내역 추가", notes = "세금 내역 정보를 입력받아 insert후 그 결과를 반환")
    @PostMapping("")
    public boolean insertTaxHistory(@RequestBody TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest) {
        return taxService.insertTaxHistory(taxHistoryInsertUpdateRequest);
    }

    @ApiOperation(value = "세금 내역 수정", notes = "세금 내역 정보를 입력받아 update후 그 결과를 반환")
    @PutMapping("/history")
    public boolean updateTaxHistory(@RequestBody TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest) {
        return taxService.updateTaxHistory(taxHistoryInsertUpdateRequest);
    }
}
