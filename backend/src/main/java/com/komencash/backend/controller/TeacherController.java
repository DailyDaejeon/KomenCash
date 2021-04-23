package com.komencash.backend.controller;

import com.komencash.backend.dto.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.TeacherSelectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("")
    public boolean saveTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.saveTeacher(teacherInsertUpdateRequest);
    }

    @GetMapping("/{teacher_id}")
    public TeacherSelectResponse findTeacher(@PathVariable("teacher_id") int teacher_id){
        return teacherService.findTeacher(teacher_id);
    }

    @PutMapping("")
    public boolean updateTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.updateTeacher(teacherInsertUpdateRequest);
    }

    @DeleteMapping("/{teacher_id}")
    public boolean deleteTeacher(@PathVariable("teacher_id") int teacher_id) {
        return teacherService.deleteTeacher(teacher_id);
    }
    

}
