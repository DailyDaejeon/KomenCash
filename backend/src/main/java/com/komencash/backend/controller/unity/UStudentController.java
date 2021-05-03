package com.komencash.backend.controller.unity;

import com.komencash.backend.dto.student.StudentJoinRequestDto;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.service.JobService;
import com.komencash.backend.service.UStudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unity")
public class UStudentController {
    @Autowired
    UStudentService ustudentService;


    @ApiOperation(value="회원 가입 요청", notes = "먼저 회원을 추가 한뒤 code에 맞게 request 요청")
    @PostMapping("/student")
    public boolean requestJoin(@RequestBody StudentJoinRequestDto request){
        Student student = ustudentService.joinStudent(request);
        if(student != null){           // 코드가 일치하는 그룹이 있고, 학생 가입이 완료 됐으면,
            ustudentService.addJoinRequest(student);
        }
        else{
            return false;
        }
        return true;
    }

}
