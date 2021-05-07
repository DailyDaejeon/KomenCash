package com.komencash.backend.dto.certificate;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateAcceptUpdateRequest {

    private int requestId;
    private Accept accept;
}
