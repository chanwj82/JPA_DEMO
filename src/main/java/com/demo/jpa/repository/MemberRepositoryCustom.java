package com.demo.jpa.repository;

import com.demo.jpa.model.MemberModel;

public interface MemberRepositoryCustom {
    public MemberModel getMemberByMbrNo(String mbrNo);
    public MemberModel getMemberByMbrNm(String cmpyNo, String mbrNm);
}
