package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.AccountResponseDto;
import com.komencash.backend.service.BankService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bank")
public class BankController {
    @Autowired
    BankService bankSerivce;


    @ApiOperation(value="그룹원들 계좌 조회", notes = "그룹원 계좌 조회")
    @GetMapping("{group_id}")
    public ResponseEntity<List<AccountResponseDto>> getAccounts(@PathVariable("group_id") int groupId){
        List<AccountResponseDto> result = bankSerivce.getAccounts(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

}
