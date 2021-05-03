package com.komencash.backend.repository;

import com.komencash.backend.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByTeacher_Id(int id);
}
