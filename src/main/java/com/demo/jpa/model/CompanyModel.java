package com.demo.jpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyModel {
	
	private String cmpyNo;
	private String cmpyNm;
	private String huafMgmtStdCd;
	private String pgTpCd;
	private int foLoginVldHh;
}
