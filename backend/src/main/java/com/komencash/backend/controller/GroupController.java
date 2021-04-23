package com.komencash.backend.controller;

import com.komencash.backend.dto.GroupInsertUpdateRequest;
import com.komencash.backend.dto.GroupResponseDto;
import com.komencash.backend.service.GroupService;
import com.komencash.backend.service.JwtService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    JwtService jwtService;

    @PostMapping
    public boolean saveGroup(@RequestBody GroupInsertUpdateRequest groupInsertUpdateRequest) throws IOException {
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
        System.out.println(1111);
        System.out.println(result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}