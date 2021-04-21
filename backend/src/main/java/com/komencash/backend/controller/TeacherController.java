package com.komencash.backend.controller;

import com.komencash.backend.dto.TeacherInsertRequest;
import com.komencash.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/join")
    public boolean saveTeacher(TeacherInsertRequest teacherInsertRequest) {
        return teacherService.saveTeacher(teacherInsertRequest);
    }
    

}
