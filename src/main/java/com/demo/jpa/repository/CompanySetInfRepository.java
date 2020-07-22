package com.demo.jpa.repository;

import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.entity.CompanySetInfoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface CompanySetInfRepository extends JpaRepository<CompanySetInfoEntity, String> {
    @EntityGraph(attributePaths = "company")
    @QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_COMMENT, value = "CompanySetInfRepository.findByCmpyNo") })
    public CompanySetInfoEntity findByCmpyNo(String cmpyNo);
}
