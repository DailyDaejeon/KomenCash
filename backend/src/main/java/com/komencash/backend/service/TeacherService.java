package com.komencash.backend.service;

import com.komencash.backend.dto.TeacherInsertRequest;

public interface TeacherService {

    public boolean saveTeacher(TeacherInsertRequest teacherInsertRequest);
}
