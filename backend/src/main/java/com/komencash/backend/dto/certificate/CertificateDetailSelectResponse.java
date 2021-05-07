package com.komencash.backend.dto.certificate;

import com.komencash.backend.entity.certificate.Certificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDetailSelectResponse {

    public int id;
    public String name;
    public String acquisitionCondition;

    public CertificateDetailSelectResponse(Certificate certificate){
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.acquisitionCondition = certificate.getAcquisitionCondition();
    }
}
