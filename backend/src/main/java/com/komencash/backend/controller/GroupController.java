package com.komencash.backend.controller;

import com.komencash.backend.dto.GroupInsertUpdateRequest;
import com.komencash.backend.dto.GroupResponseDto;
import com.komencash.backend.service.GroupService;
import com.komencash.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    JwtService jwtService;

    @PostMapping
    public boolean saveGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest){
        return groupService.saveGroup(groupInsertUpdateRequest);
    }

    @GetMapping("/group_list")
    public ResponseEntity<GroupResponseDto> getGroup(HttpServletRequest request){
        int id = jwtService.getIdFromJwt(request.getHeader("auth-token"));
        GroupResponseDto result = groupService.getGroup(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // test 용
    @GetMapping("/group_list/{teacher_id}")
    public ResponseEntity<GroupResponseDto> getGroup(@PathVariable("teacher_id") int tid,  HttpServletRequest request){
//        int id = jwtService.getIdFromJwt(request.getHeader("auth-token"));
        GroupResponseDto result = groupService.getGroup(tid);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("")
    public ResponseEntity<Void> updateGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest, HttpServletRequest request){
        groupService.updateGroup(groupInsertUpdateRequest);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }
    @DeleteMapping("{group_id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("group_id") int group_id, HttpServletRequest request){
        groupService.deleteGroup(group_id);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

}
