package com.demo.jpa.entity;

import javax.persistence.*;

import lombok.*;

@Table(name = "TMBR_MBR_BAS")
@Entity
@Data
@NoArgsConstructor
@ToString(of = {"mbrNo","cmpyNo","mbrNm"})
@IdClass(MemberEntityPK.class)
public class MemberEntity {
	@Id
	@Column(name = "mbr_no")
	private String mbrNo;
	@Id
	@Column(name = "cmpy_no")
	private String cmpyNo;
	@Column(name = "mbr_nm", nullable = false)
	private String mbrNm;

	@ManyToOne
	@JoinColumn(name = "cmpy_no", insertable = false, updatable = false)
	private CompanyBaseEntity company;

	public MemberEntity(String mbrNo, String mbrNm, CompanyBaseEntity company) {
		this.mbrNo = mbrNo;
		this.mbrNm = mbrNm;
		setCompany(company);
	}

	private void setCompany(CompanyBaseEntity company) {
		this.cmpyNo = company.getCmpyNo();
		this.company = company;
		company.getMembers().add(this);
	}
}
