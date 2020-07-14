package com.demo.jpa;

import com.demo.jpa.common.PageRequest;
import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.jpa.QueryHints;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

@SpringBootTest
class JpaDemoApplicationTests {

	private static Logger log = LoggerFactory.getLogger(JpaDemoApplicationTests.class);

	@Test
	void contextLoads() {
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
