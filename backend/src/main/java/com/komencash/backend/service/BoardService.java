package com.komencash.backend.service;

import com.komencash.backend.dto.board.BoardAddUpdateRequestDto;
import com.komencash.backend.dto.board.BoardFindResponseDto;
import com.komencash.backend.entity.board.Board;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.repository.BoardRepository;
import com.komencash.backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    GroupRepository groupRepository;

    public List<BoardFindResponseDto> findBoardList(int groupId){
        List<BoardFindResponseDto> boardFindResponseDtos = new ArrayList<>();

        List<Board> boards = boardRepository.findByGroup_Id(groupId);
        boards.forEach(board -> boardFindResponseDtos.add(new BoardFindResponseDto(board)));

        return boardFindResponseDtos;
    }


    public BoardFindResponseDto findBoard(int boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board == null) return null;

        BoardFindResponseDto boardFindResponseDto = new BoardFindResponseDto(board);
        return boardFindResponseDto;
    }


    public boolean addBoard(BoardAddUpdateRequestDto boardAddUpdateRequestDto){
        Group group = groupRepository.findById(boardAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Board board = new Board(boardAddUpdateRequestDto, group);
        boardRepository.save(board);
        return true;
    }


    public boolean updateBoard(BoardAddUpdateRequestDto boardAddUpdateRequestDto) {
        Board board = boardRepository.findById(boardAddUpdateRequestDto.getId()).orElse(null);
        if(board == null) return false;

        board.updateBoard(boardAddUpdateRequestDto);
        boardRepository.save(board);
        return true;
    }


    public boolean deleteBoard(int boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board == null) return false;

        boardRepository.delete(board);
        return true;
    }
}
