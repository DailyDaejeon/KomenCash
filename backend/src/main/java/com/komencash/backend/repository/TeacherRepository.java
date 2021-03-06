package com.komencash.backend.repository;

import com.komencash.backend.entity.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByNickname(String nickname);

    Optional<Teacher> findByEmailAndPassword(String email, String password);

    Optional<Teacher> findByPhoneNumber(String phoneNumber);
}
