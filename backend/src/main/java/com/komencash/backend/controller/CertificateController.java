package com.komencash.backend.controller;

import com.komencash.backend.dto.certificate.CertificateInsertUpdateRequest;
import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.dto.job.JobInsertUpdateRequest;
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


    @ApiOperation(value = "자격증 정보 추가", notes = "자격증 정보를 입력받아서 추가")
    @PostMapping("")
    public boolean saveCertificate(@RequestBody CertificateInsertUpdateRequest certificateInsertUpdateRequest) {
        return certificateService.saveCertificate(certificateInsertUpdateRequest);
    }


    @ApiOperation(value = "학생별 자격증 취득 목록 조회", notes = "입력받은 학생아이디로 자격증 취득 목록을 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/student/{student-id}")
    public List<CertificateSelectResponse> findCertificateListByStudent(@PathVariable("student-id") int studentId){
        return certificateService.findCertificateListByStudent(studentId);
    }
}
