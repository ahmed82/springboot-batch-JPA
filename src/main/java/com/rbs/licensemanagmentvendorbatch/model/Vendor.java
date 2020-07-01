package com.rbs.licensemanagmentvendorbatch.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Vendor {

	@Id
	private int VENDOR_ID;
	private String AGENCY_NUMBER;
	private String PADDED_AGENCY_NUMBER;
	private String NAME;
	private String CMPY;
	private String ADDR_LINE_1;
	private String ADDR_LINE_2;
	private String ADDR_LINE_3;
	private String CITY;
	private String STATE;
	private String ZIP;
	private String PHONE;
	private String FAX;
	private String CNTCT_NAME;

}
