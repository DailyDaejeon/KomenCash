package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.AccountResponseDto;
import com.komencash.backend.dto.bank.FinancialProductDetailResponseDto;
import com.komencash.backend.dto.bank.FinancialProductResponseDto;
import com.komencash.backend.service.BankService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    BankService bankService;


    @ApiOperation(value="그룹원들 계좌 조회", notes = "그룹원 계좌 조회")
    @GetMapping("/{group_id}")
    public ResponseEntity<List<AccountResponseDto>> getAccounts(@PathVariable("group_id") int groupId){
        List<AccountResponseDto> result = bankService.getAccounts(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value="은행 금융상품 생성", notes = "금융 상품 만들기")
    @PostMapping("/{group_id}/financial_product")
    public boolean createFinancialProduct(@PathVariable("group_id") int groupId, @RequestBody String name){
        bankService.createFinancialProduct(groupId, name);
        return true;
    }

    @ApiOperation(value="은행 금융상품 상세 생성", notes = "몇개월짜리 인지~~ 등등")
    @PostMapping("/{financial_product_id}/financial_product_detail")
    public boolean createFinancialProductDetail(@PathVariable("financial_product_id") int financial_product_id, @RequestBody FinancialProductDetailResponseDto dto){
        bankService.createFinancialProductDetail(financial_product_id, dto);
        return true;
    }

    @ApiOperation(value="은행 금융상품 조회", notes = "금융 상품 조회")
    @GetMapping("/{group_id}/financial_product")
    public ResponseEntity<List<FinancialProductDetailResponseDto>> getFinancialProduct(@PathVariable("group_id") int groupId){
        List<FinancialProductDetailResponseDto> result= bankService.getFinancialProductDetail(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
