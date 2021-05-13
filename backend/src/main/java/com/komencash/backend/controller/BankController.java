package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.*;
import com.komencash.backend.service.BankService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value="그룹 전체 계좌 목록 조회", notes = "입력받은 그룹 아이디의 모든 학생 계좌 정보 조회")
    @GetMapping("/{group-id}")
    public ResponseEntity<List<AccountFindResponseDto>> getAccountList(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findAccountByGroupId(groupId));
    }


    @ApiOperation(value="학생별 계좌 잔액 조회", notes = "입력받은 학생 아이디로 학생의 계좌 잔액을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/balance/{student-id}")
    public ResponseEntity<Integer> getBalance(@PathVariable("student-id") int studentId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findBalance(studentId));
    }


    @ApiOperation(value="금융 상품 생성", notes = "입력받은 그룹 아이디와 이름으로 금융 상품 생성하고 생성한 상품 아이디 반환")
    @PostMapping("/financial-product")
    public int addFinancialProduct(@RequestBody FinancialProductAddUpdateRequestDto financialProductAddUpdateRequestDto){
        return bankService.addFinancialProduct(financialProductAddUpdateRequestDto);
    }


    @ApiOperation(value="금융 상품 상세 정보 생성", notes = "하나의 금융 상품 안에 기간, 신용등금에 따른 이율 정보를 생성하고 결과를 반환")
    @PostMapping("/{financial-product-id}/financial-product-detail")
    public boolean addFinancialProductDetail(@PathVariable("financial-product-id") int financialProductId,
                                             @RequestBody FinancialProductDetailAddUpdateRequestDto FinancialProductDetailAddUpdateRequestDto){
        return bankService.addFinancialProductDetail(financialProductId, FinancialProductDetailAddUpdateRequestDto);
    }


    @ApiOperation(value="금융 상품 정보 목록 조회", notes = "입력받은 그룹 아이디의 금융 상품 목록 조회")
    @GetMapping("/{group-id}/financial-product")
    public ResponseEntity<List<FinancialProductFindResponseDto>> getFinancialProductList(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findFinancialProductList(groupId));
    }


    @ApiOperation(value="금융 상품 상세 정보 조회", notes = "입력받은 금융 상품 아이디로 금융 상품 상세정보를 조회")
    @ApiImplicitParam(name = "product-id", value = "product-id(금융상품 아이디)", dataType = "int", required = true)
    @GetMapping("/financial-product/product/{product-id}")
    public ResponseEntity<FinancialProductFindDetailResponseDto> getFinancialProduct(@PathVariable("product-id") int productId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findFinancialProductListDetail(productId));
    }


    @ApiOperation(value="금융 상품 가입 이력 조회", notes = "입력받은 학생 아이디로 학생이 가입했던 금융상품 이력을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/financial-product/student/{student-id}")
    public ResponseEntity<List<FinancialProductHistoryFindResponseDto>> getFinancialProductHistory(@PathVariable("student-id") int studentId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findFinancialProductHistoryByStudentId(studentId));
    }


    @ApiOperation(value="금융 상품 신청 조회", notes = "입력받은 상품에서 가입 신청한 목록 조회")
    @ApiImplicitParam(name = "product-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/financial-product/apply/{product-id}")
    public ResponseEntity<List<FinancialProductApplyListResponseDto>> getFinancialProductApplyList(@PathVariable("product-id") int productId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.findFinancialProductApplyList(productId));
    }


    @ApiOperation(value = "금융 상품 정보 수정", notes = "입력받은 금융 상품 정보를 update후 결과 반환")
    @PutMapping("/financial-product")
    public boolean updateFinancialProduct(@RequestBody FinancialProductAddUpdateRequestDto financialProductAddUpdateRequestDto) {
        return bankService.updateFinancialProduct(financialProductAddUpdateRequestDto);
    }


    @ApiOperation(value = "금융 상품 상세정보 수정", notes = "금융상품 상세정보를 받아서 update후 반환")
    @PutMapping("/financial-product-detail")
    public boolean updateFinancialProductDetail(@RequestBody FinancialProductDetailUpdateRequestDto financialProductDetailUpdateRequestDto) {
        return bankService.updateFinancialProductDetail(financialProductDetailUpdateRequestDto);
    }


    @ApiOperation(value = "금융상품 신청 상태 수정", notes = "금융상품 신청상태를 Deposit이나, Terminate로 바꾸기")
    @PutMapping("/financial-status-accept/{financial-product-history-id}")
    public boolean updateFinancialStatusAccept(@PathVariable("financial-product-history-id") int financialProductHistoryId){
        return bankService.updateFinancialStatusAccept(financialProductHistoryId);
    }


    @ApiOperation(value="금융 상품 삭제", notes = "입력받은 금융 상품 아이디로 삭제하고 결과 반환")
    @DeleteMapping("/financial-product/{product-id}")
    public ResponseEntity<Boolean> deleteFinancialProduct(@PathVariable("product-id") int productId){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.deleteFinancialProduct(productId));
    }


    @ApiOperation(value="학생 월급 지급 요청 생성", notes = "입력받은 그룹 아이디의 모든 학생 월급 지급 요청을 생성하고 그 결과를 반환")
    @PostMapping("/salary-payment-request")
    public boolean addSalaryPaymentRequest(@RequestBody SalaryRequestBodyDto data){
        int groupId = data.getData();
        return bankService.addSalaryPaymentRequest(groupId);
    }
}
