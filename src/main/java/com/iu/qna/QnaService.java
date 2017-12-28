package com.iu.qna;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.iu.board.BoardDTO;
import com.iu.board.BoardService;
import com.iu.file.FileDAO;
import com.iu.file.FileDTO;
import com.iu.notice.NoticeDAO;
import com.iu.notice.NoticeDTO;
import com.iu.util.FileSaver;
import com.iu.util.ListData;
import com.iu.util.Pager;
import com.iu.util.RowNum;
@Component
public class QnaService implements BoardService {

	@Autowired
	private QnaDAO qnaDAO;
	@Inject
	private FileSaver fileSaver;
	@Inject
	private FileDAO fileDAO;
	
	
	
	/*public void setQnaDAO(QnaDAO qnaDAO) {
		this.qnaDAO = qnaDAO; //setter만
	}*/

	@Override
	public int insert(BoardDTO boardDTO, HttpSession session) throws Exception {
		int num = qnaDAO.getNum();
		String filePath = session.getServletContext().getRealPath("resources/upload");
		ArrayList<FileDTO> names = new ArrayList<FileDTO>();
		
		FileDTO fileDTO=null;
		for(MultipartFile f : ((QnaDTO)boardDTO).getF1()){
			fileDTO = new FileDTO();
			fileDTO.setFilename(fileSaver.save2(filePath, f));
			fileDTO.setOriname(f.getOriginalFilename());
			fileDTO.setNum(num);
			names.add(fileDTO);
			fileDAO.insert(fileDTO);
		}
		boardDTO.setNum(num);
		return qnaDAO.insert(boardDTO);
	}

	@Override
	public int update(BoardDTO boardDTO, HttpSession session) throws Exception {
		int result = qnaDAO.update(boardDTO);
		MultipartFile [] ar = ((QnaDTO)boardDTO).getF1();
		for(MultipartFile f : ar){
			FileDTO fileDTO = new FileDTO();
			fileDTO.setNum(boardDTO.getNum());
			
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
		int result = qnaDAO.delete(num);
		List<FileDTO> ar = fileDAO.selectList(num);
		fileDAO.delete(num);
		String filePath = session.getServletContext().getRealPath("resources/upload");
		
		for(FileDTO fileDTO : ar){
			File file = new File(filePath, fileDTO.getFilename());
			file.delete();
		}
		return result;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		
		qnaDAO.hitUpdate(num);
		QnaDTO qnaDTO = (QnaDTO)qnaDAO.selectOne(num);
		List<FileDTO> ar = fileDAO.selectList(num);
		qnaDTO.setAr(ar);
		return qnaDTO;
	}

	@Override
	public void selectList(ListData listData, Model model) throws Exception {
		RowNum rowNum = listData.makeRow();
		Pager pager = listData.makepage(qnaDAO.getTotalCount(rowNum));
		model.addAttribute("list", qnaDAO.selectList(rowNum));
		model.addAttribute("pager", pager);
		
	}

}
