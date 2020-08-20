package com.demo.jpa.repository;

import com.demo.jpa.entity.CompanyBaseEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface CompanyRepository extends JpaRepository<CompanyBaseEntity, String>, CompanyRepositoryCustom {
	@EntityGraph(attributePaths = "companySetInfo")
	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_COMMENT, value = "CompanyRepository.findOneByCmpyNo") })
	public CompanyBaseEntity findOneByCmpyNo(String cmpyNo);

}
