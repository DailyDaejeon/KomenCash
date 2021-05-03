package com.komencash.backend.repository;

import com.komencash.backend.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByJob_Group_Id(int group_id);

    List<Student> findByJob_Id(int jobId);

    Student findByNickname(String nickName);

    Student findByNicknameAndJob_Group_Id(String nickname, int id);
}
