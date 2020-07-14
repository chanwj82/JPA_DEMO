package com.demo.jpa.service;

import java.util.List;

import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.jpa.repository.CompanyRepository;

@Service
public class CompanyService {

	public static Logger log = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public List<CompanyBaseEntity> getCompanyAll() {
		return companyRepository.findAll();
	}

	public CompanyBaseEntity getCompanyByCmpyNo(String cmpyNo) {
		return companyRepository.findOneByCmpyNo(cmpyNo);
	}
	
	public CompanyModel getCompany(String cmpyNo) {
		return companyRepository.getCompany(cmpyNo);
	}

	public List<MemberModel> getCompanyAllMembers(String cmpyNo) {
		return companyRepository.getCompanyAllMembers(cmpyNo);
	}

	public Page<MemberModel> getCompanyAllMembersPage(String cmpyNo, Pageable pageable) {
		return companyRepository.getCompanyAllMembersPage(cmpyNo,pageable);
	}
}
