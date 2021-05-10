package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateAcceptUpdateRequest;
import com.komencash.backend.dto.certificate.CertificateDetailSelectResponse;
import com.komencash.backend.dto.certificate.CertificateInsertUpdateRequest;
import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.entity.certificate.Certificate;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import com.komencash.backend.repository.CertificateIssueRequestHistoryRepository;
import com.komencash.backend.repository.CertificateRepository;
import com.komencash.backend.repository.GroupRepository;
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


    public boolean saveCertificate(CertificateInsertUpdateRequest certificateInsertUpdateRequest) {

        Group group = groupRepository.findById(certificateInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        Certificate certificate = new Certificate(certificateInsertUpdateRequest, group);
        certificateRepository.save(certificate);
        return true;
    }


    public boolean updateCertificate(CertificateInsertUpdateRequest certificateInsertUpdateRequest){

        Certificate certificate = certificateRepository.findById(certificateInsertUpdateRequest.getId()).orElse(null);
        if(certificate == null) return false;

        certificate.updateCertificate(certificateInsertUpdateRequest);
        certificateRepository.save(certificate);
        return true;
    }


    public List<CertificateDetailSelectResponse> findCertificateListByGroup(int groupId){

        List<CertificateDetailSelectResponse> certificateDetailSelectResponses = new ArrayList<>();

        List<Certificate> certificates = certificateRepository.findByGroup_IdOrderByNameAsc(groupId);
        certificates.forEach(certificate -> certificateDetailSelectResponses.add(new CertificateDetailSelectResponse(certificate)));

        return certificateDetailSelectResponses;
    }


    public List<CertificateSelectResponse> findCertificateListByStudent(int studentId) {

        List<CertificateSelectResponse> certificateSelectResponses = new ArrayList<>();
        List<CertificateIssueRequestHistory> certificateIssueRequestHistories
                = certificateIssueRequestHistoryRepository.findAllByStudent_Id(studentId);

        certificateIssueRequestHistories.forEach(certificateIssueRequestHistory -> {
            if(certificateIssueRequestHistory.getAccept().equals(Accept.accept))
                certificateSelectResponses.add(new CertificateSelectResponse(certificateIssueRequestHistory.getCertificate()));
        });

        return certificateSelectResponses;
    }


    public boolean deleteCertificate(int certificateId){
        Certificate certificate = certificateRepository.findById(certificateId).orElse(null);
        if(certificate == null) return false;

        certificateRepository.delete(certificate);
        return true;
    }


    public boolean updateCertificateAccept(CertificateAcceptUpdateRequest certificateAcceptUpdateRequest){
        CertificateIssueRequestHistory certificateIssueRequestHistory
                = certificateIssueRequestHistoryRepository.findById(certificateAcceptUpdateRequest.getRequestId()).orElse(null);
        if(certificateIssueRequestHistory == null) return false;

        certificateIssueRequestHistory.updateCertificateAccept(certificateAcceptUpdateRequest.getAccept());
        certificateIssueRequestHistoryRepository.save(certificateIssueRequestHistory);
        return true;
    }
}
