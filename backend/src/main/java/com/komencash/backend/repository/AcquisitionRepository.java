package com.komencash.backend.repository;

import com.komencash.backend.entity.certificate.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Integer> {
}
