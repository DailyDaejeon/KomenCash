package com.komencash.backend.controller;

import com.komencash.backend.dto.job.JobDetailResponse;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
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
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{group-id}")
    public List<JobSelectResponse> findJobByGroupId(@PathVariable("group-id") int groupId){
        return jobService.findJobByGroupId(groupId);
    }

    @ApiOperation(value = "직업 추가", notes = "직업 정보를 받아서 추가")
    @PostMapping("")
    public boolean saveJob(@RequestBody JobInsertUpdateRequest jobInsertUpdateRequest) {
        return jobService.saveJob(jobInsertUpdateRequest);
    }

    @ApiOperation(value = "직업 상세 정보 조회", notes = "입력받은 직업 아이디로 직업 상세 정보 조회")
    @ApiImplicitParam(name = "job-id", value = "job-id(직업 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{job-id}")
    public JobDetailResponse findJobDetailById(@PathVariable("job-id") int jobId){
        return jobService.findJobDetailById(jobId);
    }
}
