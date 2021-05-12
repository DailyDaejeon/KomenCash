package com.komencash.backend.controller.unity;

import com.komencash.backend.dto.bank.AccountHistoryFindResponseDto;
import com.komencash.backend.dto.bank.FinancialProductHistoryAddDto;
import com.komencash.backend.dto.bank.FinancialProductReqResponseDto;
import com.komencash.backend.dto.bank.SalaryPaymentRequestDto;
import com.komencash.backend.service.unity.UBankService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ubank")
public class UBankController {

    @Autowired
    UBankService uBankService;


    @ApiOperation(value="내 계좌 조회", notes = "내(학생아이디) 계좌 조회하기")
    @GetMapping("/myAccount/{student_id}")
    public ResponseEntity<List<AccountHistoryFindResponseDto>> getMyAccount(@PathVariable("student_id") int studentId){
        List<AccountHistoryFindResponseDto> result = uBankService.getAccountHistory(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(value="금융 상품에 가입하기", notes = "금융상품에 가입하기 신청시 before-deposit으로 넘어가서 승인받아야함")
    @PostMapping("/product-regist")
    public ResponseEntity<Boolean> productRegist(@RequestBody FinancialProductHistoryAddDto request){
        uBankService.productRegist(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @ApiOperation(value="금융 상품 중도 해지신청", notes = "금융상품에 가입하기 신청시 before-termination으로 넘어가서 승인받아야함")
    @PutMapping("/financial-product/termination")
    public ResponseEntity<Boolean> productTerminateRequest(@RequestBody FinancialProductHistoryAddDto request){
        uBankService.productTerminateRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @ApiOperation(value="은행원 - 월급 요청 목록 보기", notes = "월급 요청 목록 보기")
    @GetMapping("/salary-payment/{group-id}")
    public ResponseEntity<List<SalaryPaymentRequestDto>> getSalaryRequest(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(uBankService.getSalaryRequestList(groupId));
    }


    @ApiOperation(value = "그룹 내 금융상품 관련 요청 조회", notes = "해당 그룹의 금융상품 신청/중도해지 요청을 조회")
    @GetMapping("/ubank/financial-product/request-list/{group-id}")
    public ResponseEntity<List<FinancialProductReqResponseDto>> getFinancialProductRequestList(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(uBankService.getFinancialProductRequestList(groupId));
    }

    // 수락 버튼을 누른다
    // 국무총리, 부총리 인 경우 (vote인 경우) 는 세금에서 빼서 어카운트에 추가
    // 나머지 직업군들은 원래 salary에서 세금을 떼고 추가 , 세금에는 세금뗀 값을 추가
    @ApiOperation(value="은행원 - 월급 요청 수락하기", notes = "수락 버튼 누르면 voted, resume 직업별로 tax 적용해서 월급 적용, 요청은 삭제처리")
    @DeleteMapping("/salary-payment")
    public ResponseEntity<Boolean> salaryRequestAllow(@RequestBody SalaryPaymentRequestDto request){
        System.out.println(request.getId()+", "+ request.getStudent_id());
        uBankService.salaryRequestAllow(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }



}
