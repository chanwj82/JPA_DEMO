package com.demo.jpa.controller;

import com.demo.jpa.entity.MemberEntity;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	
	private static Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("/welcome")
	public String welocme() {
		return "hello etbs string-boot+jpa+querydsl front";
	}
	
	@RequestMapping(value = "/getMemberByMbrNo/{mbrNo}")
	public MemberEntity getMemberByMbrNo(@PathVariable("mbrNo") String mbrNo) {
		return memberService.getMemberByMbrNo(mbrNo);
	}
	
	@RequestMapping(value = "/getMember/{mbrNo}")
	public MemberModel getMember(@PathVariable("mbrNo") String mbrNo) {
		return memberService.getMember(mbrNo);
	}
	
	@RequestMapping(value = "/getMember/{cmpyNo}/{mbrNm}")
	public MemberModel getMemberByMbrNm(@PathVariable("cmpyNo") String cmpyNo, @PathVariable("mbrNm") String mbrNm) {
		return memberService.getMemberByMbrNm(cmpyNo, mbrNm);
	}
	
}
