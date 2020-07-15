package com.demo.jpa.repository;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

import java.util.List;

import javax.persistence.QueryHint;

import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.entity.CompanyBaseEntity;
import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.QueryResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.JpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

public interface CompanyRepository extends JpaRepository<CompanyBaseEntity, String> {

	public static Logger log = LoggerFactory.getLogger(CompanyRepository.class);

	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_COMMENT, value = "CompanyRepository.findOneByCmpyNo") })
	public CompanyBaseEntity findOneByCmpyNo(String cmpyNo);

	public default CompanyModel getCompany(String cmpyNo) {
		
		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");

		CompanyModel company = queryFactory
				.select(
					Projections.fields(CompanyModel.class
							, companyBaseEntity.cmpyNo
							, companyBaseEntity.cmpyNm
							, companySetInfoEntity.huafMgmtStdCd
							, companySetInfoEntity.pgTpCd
							, companySetInfoEntity.foLoginVldHh
						))
				.setHint(org.hibernate.jpa.QueryHints.HINT_COMMENT, ClzMethodUtil.getCurClzMethodName(new Object(){}.getClass()))
				.from(companyBaseEntity)
				.join(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
				.where(companyBaseEntity.cmpyNo.eq(cmpyNo))
				.fetchOne();
		
		
		/*
		JPQLQuery<?> query = new JPAQuery<>((EntityManager) SpringApplicationContext.getBean(EntityManager.class));
		
		CompanyModel company = query.select(
				Projections.fields(CompanyModel.class
						, companyBaseEntity.cmpyNo
						, companyBaseEntity.cmpyNm
						, companySetInfoEntity.huafMgmtStdCd
						, companySetInfoEntity.pgTpCd
						, companySetInfoEntity.foLoginVldHh
					))
			.from(companyBaseEntity)
			.join(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
			.where(companyBaseEntity.cmpyNo.eq(cmpyNo))
			.fetchOne();
		*/
		return company;
	}
	
	public default List<MemberModel> getCompanyAllMembers(String cmpyNo) {
		
		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");
		
		List<MemberModel> members = queryFactory
				.select(
					Projections.fields(MemberModel.class
							, memberEntity.mbrNo
							, memberEntity.mbrNm
							, companyBaseEntity.cmpyNo
							, companyBaseEntity.cmpyNm
							, companySetInfoEntity.huafMgmtStdCd
							, companySetInfoEntity.pgTpCd
							, companySetInfoEntity.foLoginVldHh
						))
				.setHint(org.hibernate.jpa.QueryHints.HINT_COMMENT, ClzMethodUtil.getCurClzMethodName(new Object(){}.getClass()))
				.from(memberEntity)
				.join(memberEntity.company,companyBaseEntity)
				//.join(companyBaseEntity).on(memberEntity.cmpyNo.eq(companyBaseEntity.cmpyNo))
				.join(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
				.where(companyBaseEntity.cmpyNo.eq(cmpyNo))
				.orderBy(memberEntity.mbrNm.desc())
				.limit(100)
				.fetch();
		
		return members;
	}

	public default Page<MemberModel> getCompanyAllMembersPage(String cmpyNo, Pageable pageable) {

		log.info("pageable.getOffset() [{}], pageable.getPageSize() [{}]",pageable.getOffset(),pageable.getPageSize());

		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");

		QueryResults<MemberModel> members = queryFactory
				.select(
						Projections.fields(MemberModel.class
								, memberEntity.mbrNo
								, memberEntity.mbrNm
								, companyBaseEntity.cmpyNo
								, companyBaseEntity.cmpyNm
								, companySetInfoEntity.huafMgmtStdCd
								, companySetInfoEntity.pgTpCd
								, companySetInfoEntity.foLoginVldHh
						))
				.setHint(org.hibernate.jpa.QueryHints.HINT_COMMENT, ClzMethodUtil.getCurClzMethodName(new Object(){}.getClass()))
				.from(memberEntity)
				.join(memberEntity.company,companyBaseEntity)
				//.join(companyBaseEntity).on(memberEntity.cmpyNo.eq(companyBaseEntity.cmpyNo))
				.join(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
				.where(companyBaseEntity.cmpyNo.eq(cmpyNo))
				.orderBy(memberEntity.mbrNm.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		return new PageImpl<>(members.getResults(), pageable, members.getTotal());
	}

}
