package com.komencash.backend.controller;

import com.komencash.backend.dto.certificate.*;
import com.komencash.backend.service.CertificateService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    CertificateService certificateService;


    @ApiOperation(value = "자격증 정보 생성", notes = "입력받은 자격증 생성 정보로 자격증을 생성하고 결과를 반환")
    @PostMapping
    public boolean addCertificate(@RequestBody CertificateAddUpdateRequestDto certificateAddUpdateRequestDto) {
        return certificateService.addCertificate(certificateAddUpdateRequestDto);
    }


    @ApiOperation(value = "자격증 정보 목록 조회", notes = "입력받은 그룹 아이디로 자격증 취득 목록을 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/group/{group-id}")
    public List<CertificateFindListResponseDto> getCertificateListByGroup(@PathVariable("group-id") int groupId){
        return certificateService.findCertificateListByGroup(groupId);
    }


    @ApiOperation(value = "자격증 정보 수정", notes = "자격증 정보를 받아서 update후 결과를 반환")
    @PutMapping
    public boolean updateCertificate(@RequestBody CertificateAddUpdateRequestDto certificateAddUpdateRequestDto) {
        return certificateService.updateCertificate(certificateAddUpdateRequestDto);
    }


    @ApiOperation(value = "자격증 정보 삭제", notes = "자격증 아이디를 받아서 delete 후 결과 반환")
    @ApiImplicitParam(name = "certificate-id", value = "certificate-id(자격증 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{certificate-id}")
    public boolean deleteCertificate(@PathVariable("certificate-id") int certificateId) {
        return certificateService.deleteCertificate(certificateId);
    }


    @ApiOperation(value = "자격증 발급 정보 생성", notes = "학생과 자격증 아이디를 입력받아 자격증 발급 정보를 생성하고 결과를 반환")
    @PostMapping("/issue")
    public boolean addCertificateIssue(@RequestBody CertificateIssueAddRequestDto certificateIssueAddRequestDto) {
        return certificateService.addCertificateIssue(certificateIssueAddRequestDto);
    }


    @ApiOperation(value = "학생별 자격증 취득 목록 조회", notes = "입력받은 학생아이디로 자격증 취득 목록을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/student/{student-id}")
    public List<CertificateFindListResponseDto> getCertificateAcceptList(@PathVariable("student-id") int studentId){
        return certificateService.findCertificateAcceptList(studentId);
    }


    @ApiOperation(value = "자격증 발급 요청 수락/거부", notes = "자격증 발금 요청 처리정보를 받아서 update후 결과를 반환")
    @PutMapping("/issue-request")
    public boolean updateCertificateAccept(@RequestBody CertificateAcceptUpdateRequestDto certificateAcceptUpdateRequestDto) {
        return certificateService.updateCertificateAccept(certificateAcceptUpdateRequestDto);
    }


    @ApiOperation(value = "자격증 발급 내역 삭제", notes = "자격증 발금 삭제 처리정보를 받아서 delete후 결과를 반환")
    @DeleteMapping("/issue-request")
    public boolean deleteCertificateIssueHistory(int studentId, int certificateId) {
        return certificateService.deleteCertificateIssueHistory(studentId, certificateId);
    }


    @ApiOperation(value = "자격증 발급 요청 생성", notes = "학생과 자격증 아이디를 입력받아 자격증 발급 요청를 생성하고 결과를 반환")
    @PostMapping("/issue-request")
    public boolean addCertificateIssueReq(@RequestBody CertificateIssueAddRequestDto certificateIssueAddRequestDto) {
        return certificateService.addCertificateIssueReq(certificateIssueAddRequestDto);
    }
}
