package com.komencash.backend.controller.unity;

import com.komencash.backend.dto.bank.FinancialProductRegist;
import com.komencash.backend.service.unity.UBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ubank")
public class UBankController {
    @Autowired
    UBankService uBankService;

//    @GetMapping("/product-mystate/{student_id}")
//    public ResponseEntity<Map<String, Object>> getMyState(@PathVariable{"student_id"} int studentId){
//
//    }
    @PostMapping("/product-regist")
    public ResponseEntity<Boolean> productRegist(@RequestBody FinancialProductRegist request){
        uBankService.productRegist(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
