package com.komencash.backend.controller;

import com.komencash.backend.dto.board.BoardAddUpdateRequestDto;
import com.komencash.backend.dto.board.BoardFindResponseDto;
import com.komencash.backend.service.BoardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;


    @ApiOperation(value = "공지사항 전체 조회", notes = "입력받은 group-id의 공지사항 전체 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
    public List<BoardFindResponseDto> findBoardList(@PathVariable("group-id") int groupId){
        return boardService.findBoardList(groupId);
    }


    @ApiOperation(value = "공지사항 세부 내용 조회", notes = "입력받은 board-id의 공지사항 전체 목록을 조회")
    @ApiImplicitParam(name = "board-id", value = "board-id(게시물 아이디)", dataType = "int", required = true)
    @GetMapping("/{board-id}")
    public BoardFindResponseDto findBoard(@PathVariable("board-id") int boardId){
        return boardService.findBoard(boardId);
    }


    @ApiOperation(value = "공지사항 추가", notes = "공지사항 정보를 받아서 추가하고 결과를 반환")
    @PostMapping
    public boolean addBoard(@RequestBody BoardAddUpdateRequestDto boardAddUpdateRequestDto) {
        return boardService.addBoard(boardAddUpdateRequestDto);
    }


    @ApiOperation(value = "공지사항 수정", notes = "공지사항 정보를 받아서 update후 반환")
    @PutMapping
    public boolean updateBoard(@RequestBody BoardAddUpdateRequestDto boardAddUpdateRequestDto) {
        return boardService.updateBoard(boardAddUpdateRequestDto);
    }


    @ApiOperation(value = "공지사항 삭제", notes = "board-id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "board-id", value = "board-id(게시물 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{board-id}")
    public boolean deleteBoard(@PathVariable("board-id") int boardId) {
        return boardService.deleteBoard(boardId);
    }

}
