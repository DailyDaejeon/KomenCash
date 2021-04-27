package com.komencash.backend.service;

import com.komencash.backend.dto.StudentResponseDto;
import com.komencash.backend.entity.Student;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponseDto getStudent(int group_id){
        List<Student> student = studentRepository.findAllByGroupId(group_id);
        return new StudentResponseDto(student);
    }

}
