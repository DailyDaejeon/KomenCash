package com.komencash.backend.repository;

import com.komencash.backend.entity.board.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}