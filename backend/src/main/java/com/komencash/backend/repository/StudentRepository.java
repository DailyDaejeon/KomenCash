package com.komencash.backend.repository;

import com.komencash.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByGroupId(int group_id);
}
