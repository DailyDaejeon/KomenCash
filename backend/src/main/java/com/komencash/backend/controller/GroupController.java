package com.komencash.backend.controller;

import com.komencash.backend.dto.group.GroupAddUpdateRequestDto;
import com.komencash.backend.dto.group.GroupFindResponseDto;
import com.komencash.backend.dto.job.JobAddUpdateRequestDto;
import com.komencash.backend.entity.job.RecruitType;
import com.komencash.backend.service.GroupService;
import com.komencash.backend.service.JobService;
import com.komencash.backend.service.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    JobService jobService;

    @Autowired
    JwtService jwtService;


    @ApiOperation(value = "생성", notes = "입력받은 group 정보로 그룹을 add하고 groupId를 반환")
    @PostMapping
    public int addGroup(@RequestBody GroupAddUpdateRequestDto groupAddUpdateRequestDto){
        int groupId = groupService.addGroup(groupAddUpdateRequestDto);
        groupService.initializeGroup(groupId);
        return groupId;
    }


    @ApiOperation(value = "목록 조회", notes = "전달받은 토큰의 선생님 아이디로 그룹 목록 조회")
    @GetMapping("/group-list")
    public ResponseEntity<Map<String, Object>> getGroup(HttpServletRequest request){

        Map<String, Object> resultMap = new HashMap<>();
        int teacherId = jwtService.getIdFromJwt(request.getHeader("auth-token"));

        List<GroupFindResponseDto> result = groupService.findGroup(teacherId);
        resultMap.put("result", result);

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


    // test 용
    @ApiOperation(value = "[TEST] 그룹리스트보기 선생님 아이디 넣어서 ", notes = "그룹 리스트 보기")
    @GetMapping("/group-list/{teacher-id}")
    public ResponseEntity<Map<String, Object>> getGroup(@PathVariable("teacher-id") int id,  HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        List<GroupFindResponseDto> result = groupService.findGroup(id);

        resultMap.put("result", result);
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


    @ApiOperation(value = "수정", notes = "입력받은 정보로 group을 modify하고 결과값을 boolean 타입으로 반환")
    @PutMapping
    public ResponseEntity<Boolean> updateGroup(@RequestBody GroupAddUpdateRequestDto groupAddUpdateRequestDto){
        boolean result = groupService.updateGroup(groupAddUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "삭제", notes = "입력받은 정보로 group을 remove하고 결과값을 boolean 타입으로 반환")
    @DeleteMapping("{group-id}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("group-id") int groupId){
        boolean result = groupService.deleteGroup(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
