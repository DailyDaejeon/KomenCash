package com.komencash.backend.controller;

import com.komencash.backend.dto.request.GroupMemberAddReqFindRequestDto;
import com.komencash.backend.dto.student.StudentDetailResponseDto;
import com.komencash.backend.dto.student.StudentFindResponseDto;
import com.komencash.backend.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @ApiOperation(value = "학생 목록 조회", notes = "입력받은 그룹 아이디의 학생 목록 조회")
    @GetMapping("/group/{group-id}")
    public ResponseEntity<List<StudentFindResponseDto>> getStudentList(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findStudentListByGroupId(groupId));
    }


    @ApiOperation(value="학생 상세 정보 조회", notes = "입력받은 학생 아이디의 학생 상세 정보 조회")
    @GetMapping("/{student-id}")
    public ResponseEntity<StudentDetailResponseDto> getStudentDetail(@PathVariable("student-id") int studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findStudentDetailByStudentId(studentId));
    }


    @ApiOperation(value="학생 추가 요청 목록 조회", notes = "입력받은 그룹 아이디의 학생 추가 요청 목록 조회")
    @GetMapping("/{group-id}/add-request")
    public ResponseEntity<List<GroupMemberAddReqFindRequestDto>> getStudentAddReq(@PathVariable("group-id") int groupId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findGroupMemberAddReqByGroupId(groupId));
    }


    @ApiOperation(value="그룹원 추가 요청 수락", notes = "입력받은 요청 아이디의 요청을 수락")
    @PutMapping("/accept")
    public ResponseEntity updateGroupMemberAddReqAccept(@RequestBody int requestId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateGroupMemberAddReqAccept(requestId));
    }


    @ApiOperation(value="그룹원 추가 요청 거절", notes = "입력받은 요청 아이디의 요청을 거절")
    @PutMapping("/reject")
    public ResponseEntity updateGroupMemberAddReqReject(@RequestBody int requestId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateGroupMemberAddReqReject(requestId));
    }


    @ApiOperation(value="학생 비밀번호 초기화", notes = "학생 비밀번호 초기화 (1234)")
    @PutMapping("reset-pw")
    public ResponseEntity updateStudentPasswordInit(@RequestBody int studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudentPasswordInit(studentId));
    }


    @ApiOperation(value="학생 직업 무직으로 변경", notes = "입력받은 학생 아이디의 직업을 무직으로 변경")
    @PutMapping("/job/fire")
    public boolean updateStudentJobFire(@RequestBody int studentId){
        return studentService.updateStudentJobFire(studentId);
    }


    @ApiOperation(value="학생 정보 삭제", notes = "입력받은 학생 아이디의 정보 삭제")
    @DeleteMapping("/{student-id}")
    public boolean deleteStudent(@PathVariable("student-id") int studentId){
        return studentService.deleteStudent(studentId);
    }
}
