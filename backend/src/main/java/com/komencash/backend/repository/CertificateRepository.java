package com.komencash.backend.repository;

import com.komencash.backend.entity.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository  extends JpaRepository<Certificate, Integer> {

    public List<Certificate> findByGroup_IdOrderByNameAsc(int groupId);
}
