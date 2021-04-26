package com.komencash.backend.service;

import com.komencash.backend.dto.TeacherDupCheckByEmailResponse;
import com.komencash.backend.dto.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.TeacherSelectResponse;
import com.komencash.backend.entity.Teacher;
import com.komencash.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService{

    @Autowired
    TeacherRepository teacherRepository;


    public boolean saveTeacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        teacherRepository.save(new Teacher(teacherInsertUpdateRequest));
        return true;
    }

    public TeacherDupCheckByEmailResponse dupCheckByEmail(String email){
        return new TeacherDupCheckByEmailResponse(teacherRepository.findByEmail(email).orElseGet(Teacher::new));
    }

    public boolean dupCheckByNickname(String nickname){
        return teacherRepository.findByNickname(nickname).orElse(null) == null ? true : false;
    }

    public TeacherSelectResponse findTeacher(int teacher_id) {
        return new TeacherSelectResponse(teacherRepository.findById(teacher_id).orElseGet(Teacher::new));
    }

    public boolean updateTeacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        teacherRepository.save(new Teacher(teacherInsertUpdateRequest));
        return true;
    }

    public boolean deleteTeacher(int teacher_id) {
        teacherRepository.deleteById(teacher_id);
        return true;
    }
}
