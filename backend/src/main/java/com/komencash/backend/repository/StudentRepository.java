package com.komencash.backend.repository;

import com.komencash.backend.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByJobGroup_Id(int group_id);


}
