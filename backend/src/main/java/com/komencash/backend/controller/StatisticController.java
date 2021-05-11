package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.dto.statistic.StatisticListAddRequestDto;
import com.komencash.backend.service.StatisticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @ApiOperation(value = "제출 목록 생성", notes = "입력받은 제출목록 정보로 제출목록을 생성하고 결과를 boolean 타입으로 반환")
    @PostMapping
    public boolean addStatisticList(@RequestBody StatisticListAddRequestDto statisticListAddRequestDto) {
        return statisticService.addStatisticList(statisticListAddRequestDto);
    }
}
