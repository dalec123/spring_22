package com.iu.member;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.iu.file.FileDAO;
import com.iu.file.FileDTO;
import com.iu.util.FileSaver;
import com.iu.util.ListData;
import com.iu.util.Pager;
import com.iu.util.RowNum;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;
	@Inject
	private FileSaver fileSaver;
	@Inject
	private FileDAO fileDAO;
	
	
	public int insert(MemberDTO memberDTO, HttpSession session, int num)throws Exception{
		num = memberDAO.getNum();
		String filePath = session.getServletContext().getRealPath("resources/upload");
		ArrayList<FileDTO> names = new ArrayList<FileDTO>();
		FileDTO fileDTO = null;
		for(MultipartFile f : memberDTO.getF1()){
			fileDTO = new FileDTO();
			fileDTO.setFilename(fileSaver.save2(filePath, f));
			fileDTO.setOriname(f.getOriginalFilename());
			fileDTO.setNum(num);
			names.add(fileDTO);
			fileDAO.insert(fileDTO);
		}
		memberDTO.setNum(num);
		return memberDAO.insert(memberDTO);
	}
	
	public int update(MemberDTO memberDTO, HttpSession session)throws Exception{
		int result = memberDAO.update(memberDTO);
		MultipartFile [] ar = memberDTO.getF1();
		for(MultipartFile f : ar){
			FileDTO fileDTO = new FileDTO();
			fileDTO.setNum(memberDTO.getNum());
			String filePath = session.getServletContext().getRealPath("resources/upload");
			String filename = fileSaver.save1(filePath, f);
			fileDTO.setFilename(filename);
			fileDTO.setOriname(f.getOriginalFilename());
			fileDAO.insert(fileDTO);
		}
		return result;
	}
	
	public int delete(int num,MemberDTO memberDTO, HttpSession session)throws Exception{
		int result = memberDAO.delete(memberDTO);
		List<FileDTO> ar = fileDAO.selectList(num);
		fileDAO.delete(num);
		String filePath = session.getServletContext().getRealPath("resources/upload");
		
		for(FileDTO fileDTO : ar){
			File file = new File(filePath, fileDTO.getFilename());
		}
		return result;
		
	}
	
	public MemberDTO selectOne(int num)throws Exception{
		memberDAO.hitUpdate(num);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO = memberDAO.selectOne(memberDTO);
		List<FileDTO> ar = fileDAO.selectList(num);
		memberDTO.setAr(ar);
		return memberDTO;
	}
	
	
	
	
	
	
	public MemberDTO selectList(MemberDTO memberDTO,ListData listData, Model model)throws Exception{
		RowNum rowNum = listData.makeRow();
		Pager pager = listData.makepage(memberDAO.getTotalCount(rowNum));
		model.addAttribute("list", memberDAO.selectOne(memberDTO));
		model.addAttribute("pager", pager);
		return memberDTO;
	}
	
	
}
