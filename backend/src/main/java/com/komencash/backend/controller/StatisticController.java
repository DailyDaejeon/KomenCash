package com.komencash.backend.controller;

import com.komencash.backend.dto.bank.FinancialProductAddUpdateRequestDto;
import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.dto.statistic.*;
import com.komencash.backend.dto.vote.VoteFindResponseDto;
import com.komencash.backend.service.StatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @ApiOperation(value = "제출 목록 생성", notes = "입력받은 제출목록 정보로 제출 내역 목록과 해당 그룹의 모든 학생의 " +
            "제출 내역을 생성하고 결과를 boolean 타입으로 반환")
    @PostMapping
    public boolean addStatisticList(@RequestBody StatisticListAddRequestDto statisticListAddRequestDto) {
        return statisticService.addStatisticList(statisticListAddRequestDto);
    }


    @ApiOperation(value = "제출 목록 리스트 조회", notes = "입력받은 그룹 아이디의 아직 신관위로 제출안한 제출목록들을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<StatisticListFindResponseDto> getStatisticListList(@PathVariable("group-id") int groupId) {
        return statisticService.findStatisticListList(groupId, false);
    }


    @ApiOperation(value="제출 목록 삭제", notes = "입력받은 제출 목록 아이디로 삭제하고 결과 반환")
    @ApiImplicitParam(name = "statistic-list-id", value = "statistic-list-id(제출 목록 아이디)", dataType = "int", required = true)
    @DeleteMapping("/financial-product/{statistic-list-id}")
    public boolean deleteStatisticList(@PathVariable("statistic-list-id") int statisticListId){
        return statisticService.deleteStatistidList(statisticListId);
    }


    @ApiOperation(value = "학생별 제출 내역 수정", notes = "입력받은 학생과 제출 목록 아이디로 학생의 제출 내역을 수정하고 결과를 반환")
    @PutMapping("/detail")
    public boolean updateStatisticListDetailSubmit(@RequestBody StatisticListDetailUpdateSubmitRequestDto statisticListDetailUpdateSubmitRequestDto) {
        return statisticService.updateStatisticListDetailSubmit(statisticListDetailUpdateSubmitRequestDto);
    }


    @ApiOperation(value = "제출 목록 상세 조회", notes = "입력받은 제출 목록 아이디의 상세 정보를 조회")
    @ApiImplicitParam(name = "statistic-list-id", value = "statistic-list-id(제출 목록 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{statistic-list-id}")
    public StatisticListFindDetailResponseDto getStatisticListDetail(@PathVariable("statistic-list-id") int staticListId) {
        return statisticService.findStatisticListDetail(staticListId);
    }


    @ApiOperation(value = "제출 내역 제출", notes = "신관위로 학생들 제출 내역을 제출하고 결과를 반환")
    @PutMapping("/submit")
    public boolean updateStatisticListSubmit(@RequestBody StatisticListUpdateSubmitRequestDto statisticListUpdateSubmitRequestDto) {
        return statisticService.updateStatisticListSubmit(statisticListUpdateSubmitRequestDto.getStatisticListId());
    }
}
