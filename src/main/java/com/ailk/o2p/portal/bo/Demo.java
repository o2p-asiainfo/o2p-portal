package com.ailk.o2p.portal.bo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Demo {

	@Size(min = 2, max = 6, message = "validator.rightName")
	private String rightName;
	@Size(min = 1,message="validator.rightDesc")
	private String rightDesc;
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	
	

}
