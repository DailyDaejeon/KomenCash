package com.komencash.backend.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateAddUpdateRequestDto {

    private int id;
    private String name;
    private String acquisitionCondition;
    private int groupId;

    public CertificateAddUpdateRequestDto(String name, String acquisitionCondition, int groupId){
        this.name = name;
        this.acquisitionCondition = acquisitionCondition;
        this.groupId = groupId;
    }
}
