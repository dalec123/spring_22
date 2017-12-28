package com.iu.qna;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iu.board.BoardDTO;
import com.iu.file.FileDTO;

public class QnaDTO extends BoardDTO{

	private int ref;
	private int step;
	private int depth;
	
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
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
}
