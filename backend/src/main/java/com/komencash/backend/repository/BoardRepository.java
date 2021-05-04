package com.komencash.backend.repository;

import com.komencash.backend.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findByGroup_Id(int groupId);
}
