package com.komencash.backend.controller;

import com.komencash.backend.dto.request.GroupMemberAddRequestDto;
import com.komencash.backend.dto.certificate.CertificateRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentResponseDto;
import com.komencash.backend.dto.student.StudentUpdateJobRequest;
import com.komencash.backend.service.StudentService;
import io.swagger.annotations.ApiOperation;
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
    @GetMapping("/group/{group_id}")
    public ResponseEntity<List<StudentResponseDto>> getStudentList(@PathVariable("group_id") int groupId){
        List<StudentResponseDto> result = studentService.getStudent(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value="그룹원 상세 조회", notes = "한 학생의 정보 보기")
    @GetMapping("/{student_id}")
    public ResponseEntity<StudentDetailResponseDto> getStudentDetail(@PathVariable("student_id") int studentId){
        StudentDetailResponseDto result =studentService.getStudentDetail(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value="그룹원 추가 요청 리스트 보기", notes = "그룹원 추가 요청 온 리스트 보기")
    @GetMapping("/{group_id}/add-request")
    public ResponseEntity<List<GroupMemberAddRequestDto>> getStudentJoinRequest(@PathVariable("group_id") int groupId){
        List<GroupMemberAddRequestDto> resultList = studentService.getStudentJoinRequest(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @ApiOperation(value="그룹원 추가 요청 수락하기", notes = "그룹원 추가 요청 수락하기")
    @PutMapping("/accept")
    public ResponseEntity addStudent(@RequestBody int studentId){
        studentService.addStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @ApiOperation(value="그룹원 추가 요청 거절하기", notes = "그룹원 추가 요청 거절하기")
    @PutMapping("/reject")
    public ResponseEntity rejectStudent(@RequestBody int studentId){
        studentService.rejectStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @ApiOperation(value="그룹원 자격증 변경 수락하기", notes = "그룹원 자격증 변경 수락하기")
    @PutMapping("/certificate")
    public boolean updateCertificate(@RequestBody CertificateRequestDto dto){
        studentService.updateCertificate(dto);
        return true;
    }

    @ApiOperation(value="그룹원 비밀번호 초기화", notes = "그룹원 비밀번호 초기화")
    @PutMapping("reset-pw")
    public ResponseEntity resetStudentPW(@RequestBody int studentId){
        studentService.resetPw(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @ApiOperation(value="그룹원 직업 변경", notes = "student-id, job-id 입력받아 직업 변경")
    @PutMapping("/job")
    public boolean updateStudentJob(@RequestBody StudentUpdateJobRequest studentUpdateJobRequest){
        return studentService.updateStudentJob(studentUpdateJobRequest);
    }


    @ApiOperation(value="그룹원 삭제", notes = "그룹원 삭제")
    @DeleteMapping("/{studentId}")
    public boolean deleteStudent(@PathVariable("studentId") int id){
        studentService.deleteStudent(id);
        return true;
    }
}
