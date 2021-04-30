package com.komencash.backend.controller;

import com.komencash.backend.dto.group.GroupInsertUpdateRequest;
import com.komencash.backend.dto.group.GroupResponseDto;
import com.komencash.backend.service.GroupService;
import com.komencash.backend.service.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    JwtService jwtService;

    @ApiOperation(value = "그룹생성", notes = "그룹 생성")
    @PostMapping
    public int saveGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest){
        return groupService.saveGroup(groupInsertUpdateRequest);
    }

    @ApiOperation(value = "그룹리스트 보기", notes = "그룹 리스트 보기")
    @GetMapping("/group_list")
    public ResponseEntity<GroupResponseDto> getGroup(HttpServletRequest request){
        int id = jwtService.getIdFromJwt(request.getHeader("auth-token"));
        GroupResponseDto result = groupService.getGroup(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // test 용
    @ApiOperation(value = "[TEST]그룹리스트보기 선생님 아이디 넣어서 ", notes = "그룹 리스트 보기")
    @GetMapping("/group_list/{teacher_id}")
    public ResponseEntity<GroupResponseDto> getGroup(@PathVariable("teacher_id") int tid,  HttpServletRequest request){
//        int id = jwtService.getIdFromJwt(request.getHeader("auth-token"));
        System.out.println("here");
        GroupResponseDto result = groupService.getGroup(tid);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "그룹 수정", notes = "그룹 수정")
    @PutMapping
    public ResponseEntity<Void> updateGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest, HttpServletRequest request){
        groupService.updateGroup(groupInsertUpdateRequest);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

    @ApiOperation(value = "그룹 삭제", notes = "그룹 삭제")
    @DeleteMapping("{group_id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("group_id") int group_id, HttpServletRequest request){
        groupService.deleteGroup(group_id);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

}
