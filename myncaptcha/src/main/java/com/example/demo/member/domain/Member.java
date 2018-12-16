package com.example.demo.member.domain;

public class Member {

	private int num;
	private String id;
	private String pwd;
	private String name;
	private String email;
	
	public Member() {
		
	}
	public Member(int num, String id, String pwd, String name, String email) {
		this.num = num;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Member [num=" + num + ", id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + "]";
	}
}
