package com.komencash.backend.repository;

import com.komencash.backend.entity.certificate.Acquisition;
import com.komencash.backend.entity.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Integer> {
    List<Acquisition> findByStudent_Id(int studentId);

    List<String> findCertificate_NameByStudent_Id(int studentId);
}
