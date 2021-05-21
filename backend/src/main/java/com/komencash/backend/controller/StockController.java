package com.komencash.backend.controller;

import com.komencash.backend.dto.stock.*;
import com.komencash.backend.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;


    @ApiOperation(value = "주식 종목 생성", notes = "주식 종목 정보를 받아서 생성하고 결과를 반환")
    @PostMapping
    public boolean addStock(@RequestBody StockAddUpdateRequestDto stockAddUpdateRequestDto) {
        return stockService.addStock(stockAddUpdateRequestDto);
    }


    @ApiOperation(value = "주식 가격 히스토리 생성", notes = "주식 가격 정보를 받아서 생성")
    @PostMapping("/history")
    public boolean addStockHistory(@RequestBody StockHistoryAddRequestDto stockHistoryAddRequestDto) {
        return stockService.addStockHistory(stockHistoryAddRequestDto);
    }


    @ApiOperation(value = "주식 종목 목록 조회", notes = "입력받은 group-id의 group 내 모든 주식 종목 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<StockFindResponseDto> getStockList(@PathVariable("group-id")int groupId){
        return stockService.findStockList(groupId);
    }


    @ApiOperation(value = "주식 종목별 히스토리 목록 조회", notes = "입력받은 stock-id의 모든 주식 가격 변동 내역을 조회")
    @ApiImplicitParam(name = "stock-id", value = "stock-id(주식 아이디)", dataType = "int", required = true)
    @GetMapping("/history/{stock-id}")
    public List<StockHistoryFindListResponseDto> getStockHistoryList(@PathVariable("stock-id")int stockId){
        return stockService.findStockHistoryList(stockId);
    }


    @ApiOperation(value = "학생별 주식 거래 내역 조회", notes = "입력받은 student-id의 모든 주식 거래 내역을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/deal/student/{student-id}")
    public List<StockDealHistoryFindListResponseDto> getStockDealHistoryList(@PathVariable("student-id")int studentId){
        return stockService.findStockDealHistoryList(studentId);
    }


    @ApiOperation(value = "주식 종목 정보 수정", notes = "주식 종목 정보를 받아서 정보를 수정")
    @PutMapping
    public boolean updateStock(@RequestBody StockAddUpdateRequestDto stockAddUpdateRequestDto) {
        return stockService.updateStock(stockAddUpdateRequestDto);
    }


    @ApiOperation(value = "주식 종목 정보 삭제", notes = "stock-id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "stock-id", value = "stock-id(주식 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{stock-id}")
    public boolean deleteStock(@PathVariable("stock-id") int stockId) {
        return stockService.deleteStock(stockId);
    }



    @ApiOperation(value = "주식 거래 내역 생성", notes = "입력받은 주식 거래 정보로 주식 거래 내역을 생성하고" +
            "계좌에서 그만큼 학생 계좌에서 차감한 후 결과를 반환")
    @PostMapping("/deal")
    public boolean addStockDealHistory(@RequestBody StockDealHistoryAddRequestDto stockDealHistoryAddRequestDto) {
        return stockService.addStockDealHistory(stockDealHistoryAddRequestDto);
    }


    @ApiOperation(value = "학생별 주식 보유 현황 조회", notes = "입력받은 학생 아이디의 모든 주식 보유 현황 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/deal/holding-status/{student-id}")
    public List<StockDealHistoryFindHoldingStatusDto> getStockHoldingStatus(@PathVariable("student-id")int studentId){
        return stockService.findStockHoldingStatus(studentId);
    }
}
