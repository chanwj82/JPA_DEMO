package com.demo.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntityPK implements Serializable {
    private String mbrNo;
    private String cmpyNo;
}
