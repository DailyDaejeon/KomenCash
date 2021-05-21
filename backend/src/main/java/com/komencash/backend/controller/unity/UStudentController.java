package com.komencash.backend.controller.unity;

import com.komencash.backend.dto.student.*;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.service.UStudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ustudent")
public class UStudentController {

    @Autowired
    UStudentService ustudentService;


    @ApiOperation(value="회원 가입 요청", notes = "먼저 회원을 추가 한뒤 code에 맞게 request 요청")
    @PostMapping
    public ResponseEntity<Map<String, Object>> requestJoin(@RequestBody StudentJoinRequestDto request){
        Map<String, Object> resultMap = ustudentService.joinStudent(request);
        if(resultMap.containsKey("success")){           // 코드가 일치하는 그룹이 있고, 학생 가입이 완료 됐으면,
            ustudentService.addJoinRequest((Student) resultMap.get("success"));
            resultMap.put("success", "회원가입요청 완료");
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


    @ApiOperation(value="로그인", notes = "학생 로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody StudentLoginRequestDto dto){

        return ResponseEntity.status(HttpStatus.OK).body(ustudentService.login(dto));
    }


    @ApiOperation(value="학생 정보 보기", notes = "학생 정보 보기")
    @GetMapping("/{student_id}")
    public StudentState getStudentState(@PathVariable("student_id") int studentId){
        return(ustudentService.getStudentState(studentId));

    }

    @ApiOperation(value="직업 변경 요청", notes = "직업 변경 요청")
    @PostMapping("/request-job-change")
    public boolean requestStudent(@RequestBody StudentJobRequestDto dto){
        return ustudentService.addJobRequestWithResume(dto);
    }


    @ApiOperation(value="비밀번호 변경", notes = "입력받은 비밀번호 변경 정보로 update하고 결과를 반환")
    @PutMapping("/change-pw")
    public boolean updateStudentPassword(@RequestBody StudentUpdatePasswordRequestDto studentUpdatePasswordRequestDto){
        return ustudentService.updateStudentPassword(studentUpdatePasswordRequestDto);
    }

}
