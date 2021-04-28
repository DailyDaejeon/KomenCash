package com.komencash.backend.controller;

import com.komencash.backend.dto.Request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.certificate.CertificateRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
import com.komencash.backend.repository.CertificateIssueRequestHistoryRepository;
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
    public ResponseEntity<StudentResponseDto> getStudentList(@PathVariable("group_id") int groupId){
        StudentResponseDto result = studentService.getStudent(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value="그룹원 상세 조회", notes = "한 학생의 정보 보기")
    @GetMapping("/{student_id}")
    public ResponseEntity<StudentDetailResponseDto> getStudentDetail(@PathVariable("student_id") int studentId){
        StudentDetailResponseDto result =studentService.getStudentDetail(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{group_id}/add-request")
    public ResponseEntity<List<GroupMemberAddRequestDto>> getStudentJoinRequest(@PathVariable("group_id") int groupId){
        List<GroupMemberAddRequestDto> resultList = studentService.getStudentJoinRequest(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @PostMapping
    public ResponseEntity<Void> addStudent(@RequestBody int studentId){
        studentService.addStudent(studentId);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

    @PutMapping
    public boolean updateCertificate(@RequestBody CertificateRequestDto dto){
        studentService.updateCertificate(dto);
        return true;
    }
}
