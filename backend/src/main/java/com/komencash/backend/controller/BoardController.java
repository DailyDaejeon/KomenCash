package com.komencash.backend.controller;

import com.komencash.backend.dto.board.BoardSelectResponse;
import com.komencash.backend.service.BoardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @ApiOperation(value = "공지사항 전체 조회", notes = "입력받은 group-id의 공지사항 전체 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<BoardSelectResponse> findBoardList(@PathVariable("group-id") int groupId){
        return boardService.findBoardList(groupId);
    }
}
