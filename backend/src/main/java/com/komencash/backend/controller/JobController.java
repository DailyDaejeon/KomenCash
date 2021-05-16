package com.komencash.backend.controller;

import com.komencash.backend.dto.job.*;
import com.komencash.backend.dto.request.JobAddReqFindResponseDto;
import com.komencash.backend.dto.request.JobAddReqAcceptRequestDto;
import com.komencash.backend.dto.request.ResumeFindDetailResponseDto;
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


    @ApiOperation(value = "직업 추가", notes = "입력받은 직업 정보를 add하고 결과를 반환")
    @PostMapping("")
    public boolean addJob(@RequestBody JobAddUpdateRequestDto jobAddUpdateRequestDto) {
        return jobService.addJob(jobAddUpdateRequestDto);
    }


    @ApiOperation(value = "직업 전체 목록 조회", notes = "그룹아이디의 그룹 내 모든 직업 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/{group-id}")
    public List<JobFindResponseDto> getJobListByGroupId(@PathVariable("group-id") int groupId){
        return jobService.findJobListByGroupId(groupId);
    }


    @ApiOperation(value = "직업 상세 정보 조회", notes = "입력받은 직업 아이디로 직업 상세 정보 조회")
    @ApiImplicitParam(name = "job-id", value = "job-id(직업 아이디)", dataType = "int", required = true)
    @GetMapping("/detail/{job-id}")
    public JobDetailResponse getJobDetailById(@PathVariable("job-id") int jobId){
        return jobService.findJobDetailById(jobId);
    }


    @ApiOperation(value = "직업 정보 수정", notes = "입력받은 직업정보로 직업을 update후 반환")
    @PutMapping
    public boolean updateJob(@RequestBody JobAddUpdateRequestDto jobAddUpdateRequestDto) {
        return jobService.updateJob(jobAddUpdateRequestDto);
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
    public List<JobAddReqFindResponseDto> findJobAddRequestByGroupId(@PathVariable("group-id") int groupId) {
        return jobService.findJobAddRequestByGroupId(groupId);
    }


    @ApiOperation(value = "직업 추가 요청 승인/거절", notes = "직업 추가 요청 처리정보를 받아서 update후 결과를 반환")
    @PutMapping("/add-request/accept")
    public boolean updateJobAddRequestAccept(@RequestBody JobAddReqAcceptRequestDto jobAddReqAcceptRequestDto) {
        return jobService.updateJobAddRequestAccept(jobAddReqAcceptRequestDto);
    }
    @ApiOperation(value = "학생 직업 변경", notes = "직업 아이디, 학생 아이디로 학생 직업 변경")
    @PutMapping("/job-change")
    public boolean updateJobAddRequestAccept(@RequestBody StudentJobUpdateDto studentJobUpdateDto) {
        return jobService.updateStudentJob(studentJobUpdateDto);
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
    public ResumeFindDetailResponseDto findResumeById(@PathVariable("resume-id") int resumeId) {
        return jobService.findResumeById(resumeId);
    }


    @ApiOperation(value = "이력서 제출내역 승인/거절", notes = "이력서 제출내역 처리정보를 받아서 update후 반환")
    @PutMapping("/resume-request/accept")
    public boolean updateResumeAccept(@RequestBody ResumeUpdateAcceptRequestDto resumeUpdateAcceptRequestDto) {
        return jobService.updateResumeAccept(resumeUpdateAcceptRequestDto);
    }


    @ApiOperation(value = "아르바이트 정보 생성", notes = "입력받은 아르바이트 정보를 생성하고 결과를 반환")
    @PostMapping("/part-time")
    public boolean addPartTime(@RequestBody PartTimeAddUpdateRequestDto partTimeAddUpdateRequestDto) {
        return jobService.addPartTime(partTimeAddUpdateRequestDto);
    }


    @ApiOperation(value = "아르바이트 정보 목록 조회", notes = "입력받은 그룹 아이디로 그룹 내의 모든 아르바이트 정보를 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/part-time-list/{group-id}")
    public List<PartTimeFindResponseDto> getPartTimeByGroupId(@PathVariable("group-id") int groupId){
        return jobService.findPartTimeByGroupId(groupId);
    }


    @ApiOperation(value = "아르바이트 정보 삭제", notes = "part-time-id를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "part-time-id", value = "part-time-id(아르바이트 아이디)", dataType = "int", required = true)
    @DeleteMapping("/part-time/{part-time-id}")
    public boolean deletePartTime(@PathVariable("part-time-id") int partTimeId) {
        return jobService.deletePartTime(partTimeId);
    }
}
