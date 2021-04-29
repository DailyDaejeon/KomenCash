package com.komencash.backend.controller;

import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.teacher.TeacherInsertUpdateRequest;
import com.komencash.backend.dto.teacher.TeacherSelectResponse;
import com.komencash.backend.service.JobService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @ApiOperation(value = "직업 전체 조회", notes = "그룹 내의 모든 직업 정보를 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(아이디)", dataType = "int", required = true)
    @GetMapping("/{group-id}")
    public List<JobSelectResponse> findJobByGroupId(@PathVariable("group-id") int groupId){
        return jobService.findJobByGroupId(groupId);
    }

    @ApiOperation(value = "직업 추가", notes = "직업 정보를 받아서 추가")
    @PostMapping("")
    public boolean saveJob(@RequestBody JobInsertUpdateRequest jobInsertUpdateRequest) {
        return jobService.saveJob(jobInsertUpdateRequest);
    }
}
