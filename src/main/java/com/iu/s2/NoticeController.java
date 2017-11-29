package com.iu.s2;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iu.board.BoardDTO;
import com.iu.notice.NoticeDTO;
import com.iu.notice.NoticeService;

@Controller
@RequestMapping(value="/notice/*")
public class NoticeController {
	private NoticeService noticeService;
	
	public NoticeController() {
		noticeService = new NoticeService();
	}
	
	
	@RequestMapping(value="noticeList")
	public String selectList(Model model){
		List<BoardDTO> ar = null;
		try {
			ar = noticeService.selectList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("list", ar)
		 	 .addAttribute("board", "notice");
		
		
		return "board/boardList";
		
	}
}
