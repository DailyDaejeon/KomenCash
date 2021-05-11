package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.dto.statistic.StatisticListAddRequestDto;
import com.komencash.backend.dto.statistic.StatisticListFindResponseDto;
import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.service.StatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @ApiOperation(value = "제출 목록 리스트 조회", notes = "입력받은 그룹 아이디의 아직 신관위로 제출안한 제출목록들을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<StatisticListFindResponseDto> getStatisticListList(@PathVariable("group-id") int groupId) {
        return statisticService.findStatisticListList(groupId);
    }
}
