package com.komencash.backend.repository;

import com.komencash.backend.entity.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository  extends JpaRepository<Certificate, Integer> {
}
