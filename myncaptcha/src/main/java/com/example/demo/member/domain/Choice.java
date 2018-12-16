package com.example.demo.member.domain;

public class Choice {
	String bzstNo;
	String panTypeCd;
	
	public String getBzstNo() {
		return bzstNo;
	}
	public void setBzstNo(String bzstNo) {
		this.bzstNo = bzstNo;
	}
	public String getPanTypeCd() {
		return panTypeCd;
	}
	public void setPanTypeCd(String panTypeCd) {
		this.panTypeCd = panTypeCd;
	}
	public Choice(String bzstNo, String panTypeCd) {
		super();
		this.bzstNo = bzstNo;
		this.panTypeCd = panTypeCd;
	}
	@Override
	public String toString() {
		return "Choice [bzstNo=" + bzstNo + ", panTypeCd=" + panTypeCd + "]";
	}
	


}
