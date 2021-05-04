package com.komencash.backend.controller;

import com.komencash.backend.dto.group.GroupResponseDto;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.stock.StockInsertUpdateRequest;
import com.komencash.backend.dto.stock.StockSelectResponse;
import com.komencash.backend.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @ApiOperation(value = "주식 종목 추가", notes = "주식 종목 정보를 받아서 추가")
    @PostMapping("")
    public boolean saveStock(@RequestBody StockInsertUpdateRequest stockInsertUpdateRequest) {
        return stockService.saveStock(stockInsertUpdateRequest);
    }


    @ApiOperation(value = "주식 종목 목록 조회", notes = "입력받은 group-id의 group 내 모든 주식 종목 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<StockSelectResponse> selectStockList(@PathVariable("group-id")int groupId){
        return stockService.selectStockList(groupId);
    }

    @ApiOperation(value = "주식 종목 정보 수정", notes = "주식 종목 정보를 받아서 정보를 수정")
    @PutMapping("")
    public boolean updateStock(@RequestBody StockInsertUpdateRequest stockInsertUpdateRequest) {
        return stockService.updateStock(stockInsertUpdateRequest);
    }
}
