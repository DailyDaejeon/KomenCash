package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.*;
import com.komencash.backend.dto.board.BoardInsertUpdateRequest;
import com.komencash.backend.entity.financial.FinancialProductDetail;
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


    @ApiOperation(value="그룹원들 계좌 조회", notes = "그룹원 계좌 조회")
    @GetMapping("/{group_id}")
    public ResponseEntity<List<AccountResponseDto>> getAccounts(@PathVariable("group_id") int groupId){
        List<AccountResponseDto> result = bankService.getAccounts(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value="학생별 잔액 조회", notes = "입력받은 학생 아이디로 학생의 계좌 잔액을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/balance/{student-id}")
    public ResponseEntity<Integer> getBalance(@PathVariable("student-id") int studentId){
        int result = bankService.getBalance(studentId);
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
    public boolean createFinancialProductDetail(@PathVariable("financial_product_id") int financial_product_id, @RequestBody FinancialProductDetailRequest dto){
        bankService.createFinancialProductDetail(financial_product_id, dto);
        return true;
    }


    @ApiOperation(value="은행 금융상품 삭제", notes = "금융 상품 삭제")
    @DeleteMapping("/financial_product/{product_id}")
    public ResponseEntity<Boolean> deleteFinancialProduct(@PathVariable("product_id") int productId){
        bankService.deleteFinancialProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }


    @ApiOperation(value="은행 금융상품 조회", notes = "금융 상품 조회")
    @GetMapping("/{group_id}/financial_product")
    public ResponseEntity<List<FinancialProductDetailRequest>> getFinancialProductList(@PathVariable("group_id") int groupId){
        List<FinancialProductDetailRequest> result= bankService.getFinancialProductDetail(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(value="금융상품 상세정보 조회", notes = "입력받은 금융상품 아이디로 금융 상품 상세정보를 조회")
    @ApiImplicitParam(name = "product-id", value = "product-id(금융상품 아이디)", dataType = "int", required = true)
    @GetMapping("/financial-product/product/{product-id}")
    public ResponseEntity<FinancialProductResponse> getFinancialProduct(@PathVariable("product-id") int productId){
        FinancialProductResponse financialProductResponse = bankService.getFinancialProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(financialProductResponse);
    }


    @ApiOperation(value="학생별 금융상품 가입이력 조회", notes = "입력받은 학생 아이디로 학생이 가입했던 금융상품 이력을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/financial-product/student/{student-id}")
    public ResponseEntity<List<FinancialProductHistorySelectResponse>> getFinancialProductHistory(@PathVariable("student-id") int studentId){
        List<FinancialProductHistorySelectResponse> financialProductHistorySelectResponses = bankService.getFinancialProductHistory(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(financialProductHistorySelectResponses);
    }


    @ApiOperation(value = "금융상품 정보 수정", notes = "금융상품 정보를 받아서 update후 반환")
    @PutMapping("/financial-product")
    public boolean updateFinancialProduct(@RequestBody FinancialProductUpdateRequest financialProductUpdateRequest) {
        return bankService.updateFinancialProduct(financialProductUpdateRequest);
    }


    @ApiOperation(value = "금융상품 상세정보 수정", notes = "금융상품 상세정보를 받아서 update후 반환")
    @PutMapping("financial-product-detail")
    public boolean updateFinancialProductDetail(@RequestBody FinancialProductDetailUpdateRequest financialProductDetailUpdateRequest) {
        return bankService.updateFinancialProductDetail(financialProductDetailUpdateRequest);
    }

}
