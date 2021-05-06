package com.komencash.backend.controller;

import com.komencash.backend.dto.group.GroupInsertUpdateRequest;
import com.komencash.backend.dto.group.GroupResponseDto;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
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
    JwtService jwtService;

    @Autowired
    JobService jobService;

    @ApiOperation(value = "그룹생성", notes = "그룹 생성")
    @PostMapping
    public int saveGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest){
        int groupId = groupService.saveGroup(groupInsertUpdateRequest);
        jobService.saveJob(new JobInsertUpdateRequest("무직", 0, "무직", "무직", 1000, RecruitType.resume, groupId));
        return groupId;
    }


    @ApiOperation(value = "그룹리스트 보기", notes = "그룹 리스트 보기")
    @GetMapping("/group_list")
    public ResponseEntity<Map<String, Object>> getGroup(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        int id = jwtService.getIdFromJwt(request.getHeader("auth-token"));
        List<GroupResponseDto> result = groupService.getGroup(id);

        resultMap.put("result", result);


        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


    // test 용
    @ApiOperation(value = "[TEST]그룹리스트보기 선생님 아이디 넣어서 ", notes = "그룹 리스트 보기")
    @GetMapping("/group_list/{teacher_id}")
    public ResponseEntity<Map<String, Object>> getGroup(@PathVariable("teacher_id") int id,  HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        List<GroupResponseDto> result = groupService.getGroup(id);

        resultMap.put("result", result);


        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


    @ApiOperation(value = "그룹 수정", notes = "그룹 수정")
    @PutMapping
    public ResponseEntity<Boolean> updateGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest, HttpServletRequest request){
        groupService.updateGroup(groupInsertUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    @ApiOperation(value = "그룹 삭제", notes = "그룹 삭제")
    @DeleteMapping("{group_id}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("group_id") int group_id, HttpServletRequest request){
        groupService.deleteGroup(group_id);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

}
