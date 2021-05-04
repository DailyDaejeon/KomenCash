package com.komencash.backend.service;

import com.komencash.backend.dto.board.BoardInsertUpdateRequest;
import com.komencash.backend.dto.board.BoardSelectResponse;
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

    public boolean saveBoard(BoardInsertUpdateRequest boardInsertUpdateRequest){
        Group group = groupRepository.findById(boardInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        Board board = new Board(boardInsertUpdateRequest, group);
        boardRepository.save(board);
        return true;
    }


    public boolean updateBoard(BoardInsertUpdateRequest boardInsertUpdateRequest) {
        Board board = boardRepository.findById(boardInsertUpdateRequest.getId()).orElse(null);
        if(board == null) return false;

        board.updateBoard(boardInsertUpdateRequest);
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
