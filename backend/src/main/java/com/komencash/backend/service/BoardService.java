package com.komencash.backend.service;

import com.komencash.backend.dto.board.BoardSelectResponse;
import com.komencash.backend.entity.board.Board;
import com.komencash.backend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public List<BoardSelectResponse> findBoardList(int groupId){
        List<BoardSelectResponse> boardSelectResponses = new ArrayList<>();

        List<Board> boards = boardRepository.findByGroup_Id(groupId);
        for (Board board : boards) boardSelectResponses.add(new BoardSelectResponse(board));

        return boardSelectResponses;
    }

    public BoardSelectResponse findBoard(int boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board == null) return null;

        BoardSelectResponse boardSelectResponse = new BoardSelectResponse(board);
        return boardSelectResponse;
    }
}
