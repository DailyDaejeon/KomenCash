package com.komencash.backend.service;

import com.komencash.backend.dto.TeacherInsertRequest;
import com.komencash.backend.entity.Teacher;
import com.komencash.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public boolean saveTeacher(TeacherInsertRequest teacherInsertRequest) {
        teacherRepository.save(new Teacher(teacherInsertRequest));
        return true;
    }
}
