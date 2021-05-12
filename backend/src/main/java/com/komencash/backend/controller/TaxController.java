package com.komencash.backend.controller;

import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.dto.tax.TaxHistoryFindResponseDto;
import com.komencash.backend.dto.tax.TaxRateUpdateRequestDto;
import com.komencash.backend.service.TaxService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tax")
public class TaxController {


    @Autowired
    TaxService taxService;


    @ApiOperation(value = "세금 내역 추가", notes = "세금 내역 정보를 입력받아 add하고 그 결과를 반환")
    @PostMapping("")
    public boolean addTaxHistory(@RequestBody TaxHistoryAddUpdateRequestDto taxHistoryAddUpdateRequestDto) {
        return taxService.addTaxHistory(taxHistoryAddUpdateRequestDto);
    }


    @ApiOperation(value = "세금 내역 목록 조회", notes = "입력받은 그룹 아이디의 세금 내역 목록 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{group-id}")
    public List<TaxHistoryFindResponseDto> getTaxHistoryList(@PathVariable("group-id") int groupId){
        return taxService.findTaxHistoryList(groupId);
    }


    @ApiOperation(value = "인플레이션 비율 수정", notes = "수정할 인플레이션 비율과 그룹 아이디를 입력받아 소득세율을 수정하고 그 결과를 반환")
    @PutMapping("/inflation-rate")
    public boolean updateInflationRate(@RequestBody TaxRateUpdateRequestDto taxRateUpdateRequestDto) {
        return taxService.updateInflationRate(taxRateUpdateRequestDto);
    }


    @ApiOperation(value = "소득세율 수정", notes = "수정할 소득세율과 그룹 아이디를 입력받아 소득세율을 수정하고 그 결과를 반환")
    @PutMapping("/rate")
    public boolean updateTaxRate(@RequestBody TaxRateUpdateRequestDto taxRateUpdateRequestDto) {
        return taxService.updateTaxRate(taxRateUpdateRequestDto);
    }
}
