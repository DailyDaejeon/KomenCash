package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.CertificateIssueRequestHistory;
import com.komencash.backend.repository.CertificateIssueRequestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    CertificateIssueRequestHistoryRepository certificateIssueRequestHistoryRepository;

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
}
