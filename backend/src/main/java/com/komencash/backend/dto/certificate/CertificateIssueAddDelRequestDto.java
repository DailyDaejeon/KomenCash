package com.komencash.backend.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateIssueAddDelRequestDto {
    private int studentId;
    private int certificateId;

}
