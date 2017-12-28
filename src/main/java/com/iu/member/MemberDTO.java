package com.iu.member;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iu.board.BoardDTO;
import com.iu.file.FileDTO;

public class MemberDTO extends BoardDTO{

	private String id;
	private String pw;
	private String name;
	private int age;
	private String phone;
	private String job;
	
	private MultipartFile [] f1;
	private List<FileDTO> ar;
	
	
	public MultipartFile[] getF1() {
		return f1;
	}
	public void setF1(MultipartFile[] f1) {
		this.f1 = f1;
	}
	public List<FileDTO> getAr() {
		return ar;
	}
	public void setAr(List<FileDTO> ar) {
		this.ar = ar;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	
}
