package com.komencash.backend.entity.certificate;

import com.komencash.backend.dto.certificate.CertificateAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "acquisition_condition")
    private String acquisitionCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public void updateCertificate(CertificateAddUpdateRequestDto certificateAddUpdateRequestDto) {
        this.name = certificateAddUpdateRequestDto.getName();
        this.acquisitionCondition = certificateAddUpdateRequestDto.getAcquisitionCondition();
    }

    public Certificate(CertificateAddUpdateRequestDto certificateAddUpdateRequestDto, Group group){
        this.name = certificateAddUpdateRequestDto.getName();
        this.acquisitionCondition = certificateAddUpdateRequestDto.getAcquisitionCondition();
        this.group = group;
    }
}
