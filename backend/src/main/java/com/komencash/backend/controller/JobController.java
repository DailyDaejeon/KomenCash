package com.komencash.backend.controller;

import com.komencash.backend.dto.request.JobAddReqSelectResponse;
import com.komencash.backend.dto.request.LawAddReqSelectListResponse;
import com.komencash.backend.dto.job.JobDetailResponse;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.dto.job.JobSelectResponse;
import com.komencash.backend.dto.request.ResumeDetailSelectResponse;
import com.komencash.backend.dto.request.ResumeSelectResponse;
import com.komencash.backend.service.JobService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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


    @ApiOperation(value = "직업 정보 수정", notes = "직업 정보를 받아서 update후 반환")
    @PutMapping("")
    public boolean updateJob(@RequestBody JobInsertUpdateRequest jobInsertUpdateRequest) {
        return jobService.updateJob(jobInsertUpdateRequest);
    }


    @ApiOperation(value = "직업 정보 삭제", notes = "job-id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "job-id", value = "job-id(직업 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{job-id}")
    public boolean deleteJob(@PathVariable("job-id") int jobId) {
        return jobService.deleteJob(jobId);
    }



    @ApiOperation(value = "미확인 직업 추가 요청 리스트 조회", notes = "그룹 아이디를 받아 미확인된 해당 그룹의 직업 추가 요청 리스트를 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request-list/{group-id}")
    public List<JobAddReqSelectResponse> findJobAddRequestByGroupId(@PathVariable("group-id") int groupId) {
        return jobService.findJobAddRequestByGroupId(groupId);
    }

    @ApiOperation(value = "미확인 이력서 리스트 조회", notes = "그룹 아이디를 받아 미확인된 해당 그룹의 이력서 리스트를 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/resume-list/{group-id}")
    public List<ResumeSelectResponse> findResumeListByGroupId(@PathVariable("group-id") int groupId) {
        return jobService.findResumeListByGroupId(groupId);
    }

    @ApiOperation(value = "이력서 상세 정보 조회", notes = "이력서 아이디를 받아 이력서 상세 정보를 조회")
    @ApiImplicitParam(name = "resume-id", value = "resume-id(이력서 아이디)", dataType = "int", required = true)
    @GetMapping("/resume-detail/{resume-id}")
    public ResumeDetailSelectResponse findResumeById(@PathVariable("resume-id") int resumeId) {
        return jobService.findResumeById(resumeId);
    }

}
