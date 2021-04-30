package com.komencash.backend.controller;

import com.komencash.backend.dto.teacher.*;
import com.komencash.backend.service.JwtService;
import com.komencash.backend.service.TeacherService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    JwtService jwtService;

    @Autowired
    TeacherService teacherService;


    @ApiOperation(value = "선생님 회원 가입", notes = "선생님 정보를 받아서 insert 후 결과 반환")
    @PostMapping("")
    public boolean saveTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.saveTeacher(teacherInsertUpdateRequest);
    }


    @ApiOperation(value = "아이디 중복 검사", notes = "email을 받아서 해당 email이 선생님 회원정보에 존재하는 경우 그 id와 email을 반환")
    @ApiImplicitParam(name = "email", value = "email(이메일)", dataType = "String", required = true)
    @GetMapping("/dup-chk-email/{email}")
    public TeacherDupCheckByEmailResponse dupCheckByEmail(@PathVariable("email") String email) {
        return teacherService.dupCheckByEmail(email);
    }


    @ApiOperation(value = "닉네임 중복 검사", notes = "nickname을 받아서 해당 nickname이 선생님 회원정보에 존재하는 경우 false, 존재하지 않는 경우 true")
    @ApiImplicitParam(name = "nickname", value = "nickname(닉네임)", dataType = "String", required = true)
    @GetMapping("dup-chk-nickname/{nickname}")
    public boolean dupCheckByNickname(@PathVariable("nickname") String nickname) {
        return teacherService.dupCheckByNickname(nickname);
    }


    @ApiOperation(value = "선생님 로그인", notes = "선생님 email, password를 받아서 로그인이 매치되는 정보가 있으면 JWT 토큰 발급하고 결과 반환")
    @PostMapping("/login")
    public boolean loginTeacher(@RequestBody TeacherLoginRequest teacherLoginRequest, HttpServletResponse response) {
        return teacherService.loginTeacher(teacherLoginRequest, response);
    }


    @ApiOperation(value = "선생님 정보 조회", notes = "teacher_id를 받아서 해당 id의 선생님 정보를 반환")
    @ApiImplicitParam(name = "teacher-id", value = "teacher-id(아이디)", dataType = "int", required = true)
    @GetMapping("/{teacher-id}")
    public TeacherSelectResponse findTeacher(@PathVariable("teacher-id") int teacherId){
        return teacherService.findTeacher(teacherId);
    }


    @ApiOperation(value = "선생님 정보 수정", notes = "선생님 정보를 받아서 update 후 결과 반환")
    @PutMapping("")
    public boolean updateTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.updateTeacher(teacherInsertUpdateRequest);
    }


    @ApiOperation(value = "선생님 정보 삭제", notes = "teacher_id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "teacher-id", value = "teacher-id(아이디)", dataType = "int", required = true)
    @DeleteMapping("/{teacher-id}")
    public boolean deleteTeacher(@PathVariable("teacher-id") int teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }


    @ApiOperation(value = "선생님 비밀번호 수정", notes = "선생님 아이디와 비밀번호를 받아서 update 후 결과 반환")
    @PutMapping("/change-pw")
    public boolean updateTeacherPassword(@RequestBody TeacherPasswordUpdateRequest teacherPasswordUpdateRequest) {
        return teacherService.updateTeacherPassword(teacherPasswordUpdateRequest);
    }

    
    @ApiOperation(value = "휴대폰 인증", notes = "휴대전화 번호를 입력하면 이메일과 인증번호를 반환")
    @ApiImplicitParam(name = "phoneNumber", value = "phoneNumber(휴대전화 번호)", dataType = "String", required = true)
    @GetMapping("/phone-auth")
    public TeacherAuthByPhoneResponse authTeacherByPhone(String phoneNumber){
        return teacherService.authTeacherByPhone(phoneNumber);
    }
}
