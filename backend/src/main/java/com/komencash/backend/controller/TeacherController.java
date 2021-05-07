package com.komencash.backend.controller;

import com.komencash.backend.dto.teacher.*;
import com.komencash.backend.service.JwtService;
import com.komencash.backend.service.TeacherService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtService jwtService;


    @ApiOperation(value = "회원 가입", notes = "입력받은 선생님 정보를 save하고 결과를 boolean 타입으로 반환")
    @PostMapping
    public boolean saveTeacher(@RequestBody TeacherAddModifyRequestDto teacherAddModifyRequestDto) {
        return teacherService.saveTeacher(teacherAddModifyRequestDto);
    }


    @ApiOperation(value = "이메일 중복 검사", notes = "입력받은 email의 id와 email을 반환")
    @ApiImplicitParam(name = "email", value = "email(이메일)", dataType = "String", required = true)
    @GetMapping("/dup-chk-email/{email}")
    public TeacherFindByEmailResponseDto findTeacherByEmail(@PathVariable("email") String email) {
        return teacherService.findTeacherByEmail(email);
    }


    @ApiOperation(value = "닉네임 중복 검사", notes = "입력받은 nickname이 선생님 정보에 존재하는 경우 false, 존재하지않는 경우 true를 반환")
    @ApiImplicitParam(name = "nickname", value = "nickname(닉네임)", dataType = "String", required = true)
    @GetMapping("dup-chk-nickname/{nickname}")
    public boolean findTeacherByNickname(@PathVariable("nickname") String nickname) {
        return teacherService.findTeacherByNickname(nickname);
    }


    @ApiOperation(value = "로그인", notes = "입력받은 email, password로 로그인 (JWT 토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> teacherLogin(@RequestBody TeacherLoginRequestDto teacherLoginRequestDto) {

        TeacherFindResponseDto teacherFindResponseDto = teacherService.teacherLogin(teacherLoginRequestDto);
        if(teacherFindResponseDto == null) return ResponseEntity.status(HttpStatus.OK).body(null);

        Map<String, Object> teacherLoginResultMap = new HashMap<>();
        teacherLoginResultMap.put("auth-token", jwtService.create(teacherFindResponseDto));
        return ResponseEntity.status(HttpStatus.OK).body(teacherLoginResultMap);
    }


    @ApiOperation(value = "정보 조회", notes = "입력받은 teacher-id로 선생님 정보를 반환")
    @ApiImplicitParam(name = "teacher-d", value = "teacher-id(선생님 아이디)", dataType = "int", required = true)
    @GetMapping("/{teacher-id}")
    public TeacherFindResponseDto findTeacher(@PathVariable("teacher-id") int teacherId){
        return teacherService.findTeacher(teacherId);
    }


    @ApiOperation(value = "정보 수정", notes = "입력받은 선생님 정보로 데이터를 modify후 결과값을 boolean 타입으로 반환")
    @PutMapping
    public boolean modifyTeacher(@RequestBody TeacherAddModifyRequestDto teacherAddModifyRequestDto) {
        return teacherService.modifyTeacher(teacherAddModifyRequestDto);
    }


    @ApiOperation(value = "선생님 정보 삭제", notes = "teacher_id를 받아서 remove후 결과 반환")
    @ApiImplicitParam(name = "teacher-id", value = "teacher-id(아이디)", dataType = "int", required = true)
    @DeleteMapping("/{teacher-id}")
    public boolean removeTeacher(@PathVariable("teacher-id") int teacherId) {
        return teacherService.removeTeacher(teacherId);
    }


    @ApiOperation(value = "비밀번호 수정", notes = "입력받은 아이디에 입력받은 비밀번호를 modify후 결과 반환")
    @PutMapping("/change-pw")
    public boolean modifyTeacherPassword(@RequestBody TeacherModifyPasswordRequestDto teacherModifyPasswordRequestDto) {
        return teacherService.modifyTeacherPassword(teacherModifyPasswordRequestDto);
    }

    
    @ApiOperation(value = "휴대폰 인증", notes = "휴대전화 번호를 입력하면 이메일과 인증번호를 반환")
    @ApiImplicitParam(name = "phoneNumber", value = "phoneNumber(휴대전화 번호)", dataType = "String", required = true)
    @GetMapping("/phone-auth")
    public TeacherFindByPhoneNumberResponseDto findTeacherByPhoneNumber(String phoneNumber){
        return teacherService.findTeacherByPhoneNumber(phoneNumber);
    }
}
