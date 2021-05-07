package com.komencash.backend.repository;

import com.komencash.backend.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByJob_Group_Id(int groupId);

    int countByJob_Group_Id(int groupId);

    List<Student> findByJob_Id(int jobId);

    Student findByNickname(String nickname);

    Student findByNicknameAndJob_Group_Id(String nickname, int groupId);
}
