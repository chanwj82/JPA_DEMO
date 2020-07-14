package com.demo.jpa.service;

import com.demo.jpa.entity.MemberEntity;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.repository.CompanyRepository;
import com.demo.jpa.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	public static Logger log = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public MemberEntity getMemberByMbrNo(String mbrNo) {
		return memberRepository.findOneByMbrNo(mbrNo);
	}
	
	public MemberModel getMember(String mbrNo) {
		return memberRepository.getMemberByMbrNo(mbrNo); 
	}
	
	public MemberModel getMemberByMbrNm(String cmpyNo, String mbrNo) { 
		return memberRepository.getMemberByMbrNm(cmpyNo, mbrNo); 
	}

}
