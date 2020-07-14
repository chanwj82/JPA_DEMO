package com.demo.jpa.controller;

import java.util.List;

import com.demo.jpa.common.PageRequest;
import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.service.CompanyService;
import com.demo.jpa.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComapnyController {
	
	private static Logger log = LoggerFactory.getLogger(ComapnyController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired 
	private CompanyService companyService;
	
	@RequestMapping(value = "/getCompanyAll")
	public List<CompanyBaseEntity> getCompanyAll() {
		return companyService.getCompanyAll();
	}
	
	@RequestMapping(value = "/getCompanyByCmpyNo/{cmpyNo}")
	public CompanyBaseEntity getCompanyByCmpyNo(@PathVariable("cmpyNo") String cmpyNo) {
		return companyService.getCompanyByCmpyNo(cmpyNo);
	}
	
	@RequestMapping(value = "/getCompany/{cmpyNo}")
	public CompanyModel getCompany(@PathVariable("cmpyNo") String cmpyNo) {
		return companyService.getCompany(cmpyNo);
	}
	
	@RequestMapping(value = "/getCompanyAllMembers/{cmpyNo}", produces = "application/json; charset=UTF-8")
	public List<MemberModel> getCompanyAllMembers(@PathVariable("cmpyNo") String cmpyNo) {
		List<MemberModel> members =  companyService.getCompanyAllMembers(cmpyNo);
		log.info("members.size [{}]",members.size());
		return members;
	}

	@RequestMapping(value = "/getCompanyAllMembersPage/{cmpyNo}", produces = "application/json; charset=UTF-8")
	public Page<MemberModel> getCompanyAllMembersPage(@PathVariable("cmpyNo") String cmpyNo, PageRequest pageRequest) {
		Page<MemberModel> members =  companyService.getCompanyAllMembersPage(cmpyNo, pageRequest.of());
		log.info("members.size [{}]",members.getTotalElements());
		return members;
	}
}
