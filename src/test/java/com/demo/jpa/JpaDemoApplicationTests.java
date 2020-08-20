package com.demo.jpa;

import com.demo.jpa.common.PageRequest;
import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.entity.CompanySetInfoEntity;
import com.demo.jpa.entity.MemberEntity;
import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.repository.CompanyRepository;
import com.demo.jpa.repository.CompanySetInfRepository;
import com.demo.jpa.repository.MemberRepository;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.jpa.QueryHints;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

@SpringBootTest
class JpaDemoApplicationTests {

	private static Logger log = LoggerFactory.getLogger(JpaDemoApplicationTests.class);

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CompanySetInfRepository companySetInfRepository;

	@Autowired
	MemberRepository memberRepository;

	/**
	 * 고객사, 회원 테스트 정보 생성
	 *
	 * @param
	 * @return
	 * @throws
	 * @author chanwj
	 */
	@Test
	public void setData() {
		CompanyBaseEntity cmpy100 = new CompanyBaseEntity("100","이제너두");
		CompanyBaseEntity cmpyJ01 = new CompanyBaseEntity("J01","차세대테스트");
		//companyRepository.saveAll(Arrays.asList(cmpy100,cmpyJ01));

		CompanySetInfoEntity cmpySetInf100 = new CompanySetInfoEntity("10", "SMT", 30, cmpy100);
		CompanySetInfoEntity cmpySetInfJ01 = new CompanySetInfoEntity("20", "KCP", 60, cmpyJ01);
		//companySetInfRepository.saveAll(Arrays.asList(cmpySetInf100,cmpySetInfJ01));

		MemberEntity mbr1 = new MemberEntity("100MBR0001", "정찬우", cmpy100);
		MemberEntity mbr2 = new MemberEntity("100MBR0002", "홍길동", cmpy100);
		MemberEntity mbr3 = new MemberEntity("J01MBR0001", "테스트1", cmpyJ01);
		MemberEntity mbr4 = new MemberEntity("J01MBR0002", "테스트2", cmpyJ01);
		MemberEntity mbr5 = new MemberEntity("J01MBR0003", "테스트3", cmpyJ01);
		MemberEntity mbr6 = new MemberEntity("J01MBR0004", "테스트4", cmpyJ01);
		MemberEntity mbr7 = new MemberEntity("J01MBR0005", "테스트5", cmpyJ01);
		MemberEntity mbr8 = new MemberEntity("J01MBR0006", "테스트6", cmpyJ01);
		MemberEntity mbr9 = new MemberEntity("J01MBR0007", "테스트7", cmpyJ01);
		MemberEntity mbr10 = new MemberEntity("J01MBR0008", "테스트8", cmpyJ01);
		MemberEntity mbr11 = new MemberEntity("J01MBR0009", "테스트9", cmpyJ01);
		MemberEntity mbr12 = new MemberEntity("J01MBR0010", "테스트10", cmpyJ01);
		MemberEntity mbr13 = new MemberEntity("J01MBR0011", "테스트11", cmpyJ01);
		MemberEntity mbr14 = new MemberEntity("J01MBR0012", "테스트12", cmpyJ01);
		//memberRepository.saveAll(Arrays.asList( mbr1, mbr2, mbr3, mbr4, mbr5, mbr6, mbr7, mbr8, mbr9, mbr10, mbr11, mbr12, mbr13, mbr14));
		companyRepository.saveAll(Arrays.asList(cmpy100,cmpyJ01));
	}

	/**
	 * 고객사 전체 회원 목록 조회
	 *
	 * @param
	 * @return
	 * @throws
	 * @author chanwj
	 */
	@Test
	public void getCmpyAllMembers(){
		List<MemberModel> memberList = companyRepository.getCompanyAllMembers("J01");
		memberList.forEach(mbmber -> log.info(mbmber.toString()));
	}

	@Test
	public void getCmpyAllMembersPage(){
		PageRequest pageRequest = new PageRequest();
		pageRequest.setSize(5);
		pageRequest.setPage(1);
		Page<MemberModel> members = companyRepository.getCompanyAllMembersPage("J01",pageRequest.of());
		log.info("members.size [{}]",members.getTotalElements());
	}

	@Test
	public void getCompanyBas(){
		CompanyBaseEntity companyBaseEntity = companyRepository.findOneByCmpyNo("J01");
		Assert.assertTrue(companyBaseEntity.getCmpyNm().equals("차세대테스트"));
		log.info("companyBaseEntity [{}] CompanySetInfo [{}]", companyBaseEntity, companyBaseEntity.getCompanySetInfo());

		CompanyModel companyBase = companyRepository.getCompany("100");
		log.info("companyBase [{}]", companyBase);
		Assert.assertTrue(companyBase.getCmpyNm().equals("이제너두"));
	}

	@Test
	public void getCompanySetInf(){
		CompanySetInfoEntity companySetInfoEntity = companySetInfRepository.findByCmpyNo("J01");
		log.info("companySetInfoEntity [{}] Company [{}]", companySetInfoEntity, companySetInfoEntity.getCompany());
		Assert.assertTrue(companySetInfoEntity.getCmpyNo().equals("J01"));
	}

	@Test
	public void getMemberByMbrNo() {
		MemberModel member = memberRepository.getMemberByMbrNo("100MBR0001");
		log.info("[{}]",member);
	}
}
