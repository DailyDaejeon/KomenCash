package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.*;
import com.komencash.backend.entity.certificate.Certificate;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.CertificateIssueRequestHistoryRepository;
import com.komencash.backend.repository.CertificateRepository;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    CertificateIssueRequestHistoryRepository certificateIssueRequestHistoryRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;


    public boolean addCertificate(CertificateAddUpdateRequestDto certificateAddUpdateRequestDto) {
        Group group = groupRepository.findById(certificateAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Certificate certificate = new Certificate(certificateAddUpdateRequestDto, group);
        certificateRepository.save(certificate);
        return true;
    }


    public List<CertificateFindListResponseDto> findCertificateListByGroup(int groupId){
        List<CertificateFindListResponseDto> certificateFindListResponseDtos = new ArrayList<>();

        List<Certificate> certificates = certificateRepository.findByGroup_IdOrderByNameAsc(groupId);
        certificates.forEach(certificate -> certificateFindListResponseDtos.add(new CertificateFindListResponseDto(certificate)));

        return certificateFindListResponseDtos;
    }


    public boolean updateCertificate(CertificateAddUpdateRequestDto certificateAddUpdateRequestDto){
        Certificate certificate = certificateRepository.findById(certificateAddUpdateRequestDto.getId()).orElse(null);
        if(certificate == null) return false;

        certificate.updateCertificate(certificateAddUpdateRequestDto);
        certificateRepository.save(certificate);
        return true;
    }


    public boolean deleteCertificate(int certificateId){
        Certificate certificate = certificateRepository.findById(certificateId).orElse(null);
        if(certificate == null) return false;

        certificateRepository.delete(certificate);
        return true;
    }


    public boolean addCertificateIssue(CertificateIssueAddRequestDto certificateIssueAddRequestDto){
        Certificate certificate = certificateRepository.findById(certificateIssueAddRequestDto.getCertificateId()).orElse(null);
        Student student = studentRepository.findById(certificateIssueAddRequestDto.getStudentId()).orElse(null);
        if(certificate == null || student == null) return false;

        CertificateIssueRequestHistory certificateIssueRequestHistory = new CertificateIssueRequestHistory(certificate, student, Accept.accept);
        certificateIssueRequestHistoryRepository.save(certificateIssueRequestHistory);
        return true;
    }


    public List<CertificateFindListResponseDto> findCertificateAcceptList(int studentId) {

        List<CertificateFindListResponseDto> certificateSelectResponses = new ArrayList<>();
        List<CertificateIssueRequestHistory> certificateIssueRequestHistories
                = certificateIssueRequestHistoryRepository.findByStudent_Id(studentId);

        certificateIssueRequestHistories.forEach(certificateIssueRequestHistory -> {
            if(certificateIssueRequestHistory.getAccept().equals(Accept.accept))
                certificateSelectResponses.add(new CertificateFindListResponseDto(certificateIssueRequestHistory.getCertificate()));
        });

        return certificateSelectResponses;
    }


    public boolean updateCertificateAccept(CertificateAcceptUpdateRequestDto certificateAcceptUpdateRequestDto){
        CertificateIssueRequestHistory certificateIssueRequestHistory
                = certificateIssueRequestHistoryRepository.findById(certificateAcceptUpdateRequestDto.getRequestId()).orElse(null);
        if(certificateIssueRequestHistory == null) return false;

        certificateIssueRequestHistory.updateCertificateAccept(certificateAcceptUpdateRequestDto.getAccept());
        certificateIssueRequestHistoryRepository.save(certificateIssueRequestHistory);
        return true;
    }


    public boolean deleteCertificateIssueHistory(int studentId, int certificateId){
        Student student = studentRepository.findById(studentId).orElse(null);
        Certificate certificate = certificateRepository.findById(certificateId).orElse(null);
        if(student == null || certificate == null) return false;

        CertificateIssueRequestHistory certificateIssueRequestHistory = certificateIssueRequestHistoryRepository
                .findByStudent_IdAndCertificate_Id(student.getId(), certificate.getId());
        certificateIssueRequestHistoryRepository.delete(certificateIssueRequestHistory);
        return true;
    }


    public boolean addCertificateIssueReq(CertificateIssueAddRequestDto certificateIssueAddRequestDto){
        Certificate certificate = certificateRepository.findById(certificateIssueAddRequestDto.getCertificateId()).orElse(null);
        Student student = studentRepository.findById(certificateIssueAddRequestDto.getStudentId()).orElse(null);
        if(certificate == null || student == null) return false;

        CertificateIssueRequestHistory certificateIssueRequestHistory = new CertificateIssueRequestHistory(certificate, student, Accept.before_confirm);
        certificateIssueRequestHistoryRepository.save(certificateIssueRequestHistory);
        return true;
    }
}
