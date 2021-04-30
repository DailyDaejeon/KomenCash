package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.AccountResponseDto;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/bank")
public class BankController {
    @Autowired
    BankService bankSerivce;


    @ApiOperation(value="그룹원들 계좌 조회", notes = "그룹원 계좌 조회")
    @GetMapping("/{group_id}")
    public ResponseEntity<List<AccountResponseDto>> getAccounts(@PathVariable("group_id") int groupId){
        List<AccountResponseDto> result = bankSerivce.getAccounts(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @ApiOperation(value="은행 금융상품 생성" notes = "금융 상품 만들기")
//    @PostMapping("/{group_id}/financial_product")
//    public ResponseEntity<Void> createFinancialProduct(@PathVariable("group_id") int groupId, @RequestBody String name){
//        bankSerivce.createFinancialProduct(groupId, name);
//    }
//
//    @ApiOperation(value="은행 금융상품 조회", notes = "금융 상품 조회")
//    @GetMapping("/{group_id}/financial_product")
//    public ResponseEntity<List<FinancialProductResponseDto>> getFinancialProduct(@PathVariable("group_id") int groupId){
//
//    }

}
