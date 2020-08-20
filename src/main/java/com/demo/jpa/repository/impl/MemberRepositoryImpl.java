package com.demo.jpa.repository.impl;

import com.demo.jpa.model.MemberModel;
import com.demo.jpa.repository.MemberRepositoryCustom;
import com.demo.jpa.utils.ClzMethodUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.demo.jpa.entity.QCompanyBaseEntity.companyBaseEntity;
import static com.demo.jpa.entity.QCompanySetInfoEntity.companySetInfoEntity;
import static com.demo.jpa.entity.QMemberEntity.memberEntity;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MemberModel getMemberByMbrNo(String mbrNo) {
        MemberModel member = new JPAQueryFactory(entityManager)
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
        return member;
    }

    @Override
    public MemberModel getMemberByMbrNm(String cmpyNo, String mbrNm) {
        MemberModel member = new JPAQueryFactory(entityManager)
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
        return member;
    }
}

