package com.demo.jpa.repository.impl;

import com.demo.jpa.core.SpringApplicationContext;
import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import com.demo.jpa.repository.CompanyRepositoryCustom;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    public static Logger log = LoggerFactory.getLogger(CompanyRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CompanyModel getCompany(String cmpyNo) {

        CompanyModel company = new JPAQueryFactory(entityManager)
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

    @Override
    public List<MemberModel> getCompanyAllMembers(String cmpyNo) {

        List<MemberModel> members = new JPAQueryFactory(entityManager)
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

    @Override
    public Page<MemberModel> getCompanyAllMembersPage(String cmpyNo, Pageable pageable) {

        log.info("pageable.getOffset() [{}], pageable.getPageSize() [{}]",pageable.getOffset(),pageable.getPageSize());

        QueryResults<MemberModel> members = new JPAQueryFactory(entityManager)
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
