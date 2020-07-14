package com.demo.jpa.repository;

import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.entity.MemberEntity;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, MemberEntity> {

	public static Logger log = LoggerFactory.getLogger(MemberRepository.class);
	
	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_COMMENT, value = "MemberRepository.findOneByMbrNo") })
	public MemberEntity findOneByMbrNo(String mbrNo);
	
	public default MemberModel getMemberByMbrNo(String mbrNo) {
		
		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");
		
		MemberModel member = queryFactory
					.select(Projections.fields(MemberModel.class
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
					.innerJoin(companyBaseEntity).on(memberEntity.company.cmpyNo.eq(companyBaseEntity.cmpyNo))
					.innerJoin(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
					.where(memberEntity.mbrNo.eq(mbrNo))
					.fetchOne();
		log.info("{}",member);
		return member;
	}
	
	public default MemberModel getMemberByMbrNm(String cmpyNo, String mbrNm) {
		
		JPAQueryFactory queryFactory = (JPAQueryFactory) SpringApplicationContext.getBean("queryFactory");
		
		MemberModel member = queryFactory
					.select(Projections.fields(MemberModel.class
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
					.innerJoin(companyBaseEntity).on(memberEntity.company.cmpyNo.eq(companyBaseEntity.cmpyNo))
					.innerJoin(companySetInfoEntity).on(companyBaseEntity.cmpyNo.eq(companySetInfoEntity.cmpyNo))
					.where(memberEntity.company.cmpyNo.eq(cmpyNo), memberEntity.mbrNm.eq(mbrNm))
					.fetchOne();
		log.info("{}",member);
		return member;
	}

}
