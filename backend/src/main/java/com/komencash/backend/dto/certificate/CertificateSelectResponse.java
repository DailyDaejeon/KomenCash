package com.komencash.backend.dto.certificate;

import com.komencash.backend.entity.certificate.Certificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateSelectResponse {

    private int id;
    private String name;

    public CertificateSelectResponse(Certificate certificate){
        this.id = certificate.getId();
        this.name = certificate.getName();
    }
}
