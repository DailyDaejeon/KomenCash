package com.komencash.backend.controller;

import com.komencash.backend.dto.StudentResponseDto;
import com.komencash.backend.entity.Student;
import com.komencash.backend.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @ApiOperation(value = "그룹원 목록 조회", notes = "그룹 id 입력 후 그룹원 목록 조회")
    @GetMapping("/{group_id}")
    public ResponseEntity<StudentResponseDto> getStudentList(@PathVariable("group_id") int group_id){
        StudentResponseDto result = studentService.getStudent(group_id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
