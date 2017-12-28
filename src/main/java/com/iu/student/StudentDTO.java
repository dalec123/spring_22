package com.iu.student;

import com.iu.member.MemberDTO;

public class StudentDTO extends MemberDTO{

	private String tid;
	private String birth;
	private int grade;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
