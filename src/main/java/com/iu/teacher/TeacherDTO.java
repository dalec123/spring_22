package com.iu.teacher;

import java.sql.Date;

import com.iu.member.MemberDTO;

public class TeacherDTO extends MemberDTO{
	private Date hiredate;
	private String subject;
	private int sal;
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	
	
}
