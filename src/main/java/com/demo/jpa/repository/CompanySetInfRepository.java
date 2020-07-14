package com.demo.jpa.repository;

import com.demo.jpa.entity.CompanySetInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanySetInfRepository extends JpaRepository<CompanySetInfoEntity, String> {
}
