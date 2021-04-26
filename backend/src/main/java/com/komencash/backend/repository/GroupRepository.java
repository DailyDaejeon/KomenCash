package com.komencash.backend.repository;

import com.komencash.backend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    Optional<Group> findByTeacherId(int id);
}
