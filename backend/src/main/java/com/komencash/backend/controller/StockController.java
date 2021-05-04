package com.komencash.backend.controller;

import com.komencash.backend.dto.stock.StockInsertUpdateRequest;
import com.komencash.backend.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
