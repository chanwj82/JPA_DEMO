package com.demo.jpa.repository;

import com.demo.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface MemberRepository extends JpaRepository<MemberEntity, MemberEntity>, MemberRepositoryCustom {
	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_COMMENT, value = "MemberRepository.findOneByMbrNo") })
	public MemberEntity findOneByMbrNo(String mbrNo);
}
