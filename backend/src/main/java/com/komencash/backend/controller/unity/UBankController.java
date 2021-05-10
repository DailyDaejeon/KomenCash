package com.komencash.backend.controller.unity;

import com.komencash.backend.dto.bank.AccountHistoryFindResponseDto;
import com.komencash.backend.dto.bank.FinancialProductAddDto;
import com.komencash.backend.service.unity.UBankService;
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

    @GetMapping("/myAccount/{student_id}")
    public ResponseEntity<List<AccountHistoryFindResponseDto>> getMyAccount(@PathVariable("student_id") int studentId){
        List<AccountHistoryFindResponseDto> result = uBankService.getAcocuntHistory(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/product-regist")
    public ResponseEntity<Boolean> productRegist(@RequestBody FinancialProductAddDto request){
        uBankService.productRegist(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
