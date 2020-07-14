package com.demo.jpa.entity;

import javax.persistence.*;

import lombok.*;

@Table(name = "TCMPY_SET_INF")
@Entity
@Data
@NoArgsConstructor
@ToString(of = {"cmpyNo","huafMgmtStdCd","pgTpCd","foLoginVldHh"})
public class CompanySetInfoEntity {
	@Id
	@Column(name = "cmpy_No")
	private String cmpyNo;
	@Column(name = "huaf_mgmt_std_cd", nullable = false)
	private String huafMgmtStdCd;
	@Column(name = "pg_tp_cd", nullable = false)
	private String pgTpCd;
	@Column(name = "fo_login_vld_hh", nullable = false)
	private int foLoginVldHh;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cmpy_no", insertable = false, updatable = false)
	private CompanyBaseEntity company;

	public CompanySetInfoEntity(String huafMgmtStdCd, String pgTpCd, int foLoginVldHh, CompanyBaseEntity company) {
		this.cmpyNo = company.getCmpyNo();
		this.huafMgmtStdCd = huafMgmtStdCd;
		this.pgTpCd = pgTpCd;
		this.foLoginVldHh = foLoginVldHh;
		company.setCompanySetInfo(this);
	}
}
