package com.demo.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Table(name = "TCMPY_CMPY_BAS")
@Entity
@Data
@NoArgsConstructor
@ToString(of = {"cmpyNo","cmpyNm"})
public class CompanyBaseEntity {
	@Id
	@Column(name = "cmpy_No")
	private String cmpyNo;
	@Column(name = "cmpy_Nm", nullable = false)
	private String cmpyNm;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
	private CompanySetInfoEntity companySetInfo;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<MemberEntity> members = new ArrayList<>();

	public CompanyBaseEntity(String cmpyNo, String cmpyNm) {
		this.cmpyNo	= cmpyNo;
		this.cmpyNm = cmpyNm;
	}
}
