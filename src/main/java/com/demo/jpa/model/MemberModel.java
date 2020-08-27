package com.demo.jpa.model;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
//@ToString(of = {"cmpyNm","mbrNm","huafMgmtStdCd","pgTpCd","foLoginVldHh"})
public class MemberModel {
	private String cmpyNo;
	private String cmpyNm;
	private String mbrNo;
	private String mbrNm;
	private String huafMgmtStdCd;
	private String pgTpCd;
	private int foLoginVldHh;
}
