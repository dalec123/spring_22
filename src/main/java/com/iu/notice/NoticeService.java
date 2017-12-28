package com.iu.notice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.iu.board.BoardDTO;
import com.iu.board.BoardService;
import com.iu.file.FileDAO;
import com.iu.file.FileDTO;
import com.iu.util.FileSaver;
import com.iu.util.ListData;
import com.iu.util.Pager;
import com.iu.util.RowNum;

@Service
public class NoticeService implements BoardService {

	@Autowired
	private NoticeDAO noticeDAO;
	@Inject
	private FileSaver fileSaver;
	@Inject
	private FileDAO fileDAO;

	
	
	
	@Override
	public int insert(BoardDTO boardDTO, HttpSession session) throws Exception {
		int num = noticeDAO.getNum();
		String filePath = session.getServletContext().getRealPath("resources/upload");
		ArrayList<FileDTO> names=new ArrayList<FileDTO>();
		/*FileSaver fileSaver = new FileSaver();*/
		FileDTO fileDTO=null;
		for(MultipartFile f: ((NoticeDTO)boardDTO).getF1()){
			fileDTO = new FileDTO();
			fileDTO.setFilename(fileSaver.save2(filePath, f));
			fileDTO.setOriname(f.getOriginalFilename());
			fileDTO.setNum(num);
			names.add(fileDTO);
			fileDAO.insert(fileDTO);
		}
		
		boardDTO.setNum(num);
		
		return noticeDAO.insert(boardDTO);
	}

	@Override
	public int update(BoardDTO boardDTO, HttpSession session) throws Exception {
		int result=noticeDAO.update(boardDTO);
		MultipartFile [] ar = ((NoticeDTO)boardDTO).getF1();
		for(MultipartFile f : ar){
			FileDTO fileDTO = new FileDTO();
			fileDTO.setNum(boardDTO.getNum());
			/*FileSaver fileSaver = new FileSaver();*/
			String filePath = session.getServletContext().getRealPath("resources/upload");
			String filename=fileSaver.save1(filePath, f);
			fileDTO.setFilename(filename);
			fileDTO.setOriname(f.getOriginalFilename());
			fileDAO.insert(fileDTO);
		}
		return result;
	}

	@Override
	public int delete(int num, HttpSession session) throws Exception {
		
		int result=noticeDAO.delete(num);
		List<FileDTO> ar = fileDAO.selectList(num);
		fileDAO.delete(num);
		String filePath = session.getServletContext().getRealPath("resources/upload");
		
		for(FileDTO fileDTO: ar){
			File file = new File(filePath, fileDTO.getFilename());
			file.delete();
		}
		
		return result;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		// TODO Auto-generated method stub
		noticeDAO.hitUpdate(num);
		NoticeDTO noticeDTO = (NoticeDTO)noticeDAO.selectOne(num); 
		List<FileDTO> ar=fileDAO.selectList(num);
		noticeDTO.setAr(ar);
		return noticeDTO;
	}

	@Override
	public void selectList(ListData listData, Model model) throws Exception {
		// TODO Auto-generated method stub
		
		//페이징
		RowNum rowNum = listData.makeRow();
		Pager pager = listData.makepage(noticeDAO.getTotalCount(rowNum));
		model.addAttribute("list", noticeDAO.selectList(rowNum));
		model.addAttribute("pager", pager);
	}

}
