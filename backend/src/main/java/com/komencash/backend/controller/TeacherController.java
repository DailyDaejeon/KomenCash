package com.komencash.backend.controller;

import com.komencash.backend.dto.TeacherDupCheckByEmailResponse;
import com.komencash.backend.dto.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.TeacherSelectResponse;
import com.komencash.backend.service.TeacherService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;


    @ApiOperation(value = "선생님 회원 가입", notes = "선생님 정보를 받아서 insert 후 결과 반환")
    @PostMapping("")
    public boolean saveTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.saveTeacher(teacherInsertUpdateRequest);
    }


    @ApiOperation(value = "아이디 중복 검사", notes = "email을 받아서 해당 email이 선생님 회원정보에 존재하는 경우 그 id와 email을 반환")
    @ApiImplicitParam(name = "email", value = "email(이메일)", dataType = "String", required = true)
    @GetMapping("/dup_chk_email/{email}")
    public TeacherDupCheckByEmailResponse dupCheckByEmail(@PathVariable("email") String email) {
        return teacherService.dupCheckByEmail(email);
    }


    @ApiOperation(value = "닉네임 중복 검사", notes = "nickname을 받아서 해당 nickname이 선생님 회원정보에 존재하는 경우 false, 존재하지 않는 경우 true")
    @ApiImplicitParam(name = "nickname", value = "nickname(닉네임)", dataType = "String", required = true)
    @GetMapping("dup_chk_nickname/{nickname}")
    public boolean dupCheckByNickname(@PathVariable("nickname") String nickname) {
        return teacherService.dupCheckByNickname(nickname);
    }


    @ApiOperation(value = "선생님 정보 조회", notes = "teacher_id를 받아서 해당 id의 선생님 정보를 반환")
    @ApiImplicitParam(name = "teacher_id", value = "teacher_id(아이디)", dataType = "int", required = true)
    @GetMapping("/{teacher_id}")
    public TeacherSelectResponse findTeacher(@PathVariable("teacher_id") int teacher_id){
        return teacherService.findTeacher(teacher_id);
    }


    @ApiOperation(value = "선생님 정보 수정", notes = "선생님 정보를 받아서 update 후 결과 반환")
    @PutMapping("")
    public boolean updateTeacher(@RequestBody TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        return teacherService.updateTeacher(teacherInsertUpdateRequest);
    }


    @ApiOperation(value = "선생님 정보 삭제", notes = "teacher_id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "teacher_id", value = "teacher_id(아이디)", dataType = "int", required = true)
    @DeleteMapping("/{teacher_id}")
    public boolean deleteTeacher(@PathVariable("teacher_id") int teacher_id) {
        return teacherService.deleteTeacher(teacher_id);
    }
    

}
