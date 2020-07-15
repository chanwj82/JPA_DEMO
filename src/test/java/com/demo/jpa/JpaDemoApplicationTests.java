package com.demo.jpa;

import com.demo.jpa.common.PageRequest;
import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.entity.CompanySetInfoEntity;
import com.demo.jpa.entity.MemberEntity;
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
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

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

	@PersistenceContext
	EntityManager em;

	@Test
	void contextLoads() {
	}

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

	@Test
	public void getCmpyAllMembers(){

		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");

		List<MemberModel> resultList = queryFactory
				.select(
						Projections.fields(MemberModel.class
								, memberEntity.mbrNo
								, memberEntity.mbrNm
								, companyBaseEntity.cmpyNo
								, companyBaseEntity.cmpyNm
								, companySetInfoEntity.huafMgmtStdCd
								, companySetInfoEntity.pgTpCd
								, companySetInfoEntity.foLoginVldHh
						)
				)
				.setHint(QueryHints.HINT_COMMENT, ClzMethodUtil.getCurClzMethodName(new Object(){}.getClass()))
				.from(memberEntity)
				.join(memberEntity.company,companyBaseEntity)
				.join(companySetInfoEntity).on(companySetInfoEntity.cmpyNo.eq(companyBaseEntity.cmpyNo))
				.where(memberEntity.company.cmpyNo.in("100","J01"))
				.orderBy(memberEntity.company.cmpyNo.asc(), memberEntity.mbrNm.desc())
				//.limit(3)
				//.offset(4)
				.fetch();

		log.info("resultList.size() = {}",resultList.size());
		//IntStream.range(0,resultList.size()).forEach(index -> log.info("[{}] [{}]", index+1, resultList.get(index)));
		resultList.forEach(result -> log.info("[{}]",result));

		Assert.assertEquals(resultList.size(),14);
		Assert.assertTrue(resultList.size() == 14);
	}

	@Test
	public void getCmpyAllMembersPage(){
		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");

		PageRequest pageRequest = new PageRequest();
		pageRequest.setSize(2);
		pageRequest.setPage(1);

		QueryResults<MemberModel> resultList = queryFactory
				.select(
						Projections.fields(MemberModel.class
								, memberEntity.mbrNo
								, memberEntity.mbrNm
								, companyBaseEntity.cmpyNo
								, companyBaseEntity.cmpyNm
								, companySetInfoEntity.huafMgmtStdCd
								, companySetInfoEntity.pgTpCd
								, companySetInfoEntity.foLoginVldHh
						)
				)
				.setHint(QueryHints.HINT_COMMENT, ClzMethodUtil.getCurClzMethodName(new Object(){}.getClass()))
				.from(memberEntity)
				.join(memberEntity.company,companyBaseEntity)
				.join(companySetInfoEntity).on(companySetInfoEntity.cmpyNo.eq(companyBaseEntity.cmpyNo))
				.where(memberEntity.company.cmpyNo.in("100","J01"))
				.orderBy(memberEntity.company.cmpyNo.asc(), memberEntity.mbrNm.desc())
				.limit(pageRequest.of().getPageSize())
				.offset(pageRequest.of().getOffset())
				.fetchResults();

		resultList.getResults().forEach(member -> log.info("{}",member));

	}
}
