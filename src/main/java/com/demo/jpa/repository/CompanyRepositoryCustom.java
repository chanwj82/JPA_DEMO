package com.demo.jpa.repository;

import com.demo.jpa.model.CompanyModel;
import com.demo.jpa.model.MemberModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyRepositoryCustom {
    public CompanyModel getCompany(String cmpyNo);
    public List<MemberModel> getCompanyAllMembers(String cmpyNo);
    public Page<MemberModel> getCompanyAllMembersPage(String cmpyNo, Pageable pageable);
}
