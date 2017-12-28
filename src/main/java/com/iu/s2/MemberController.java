package com.iu.s2;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iu.member.MemberDTO;
import com.iu.member.MemberService;
import com.iu.util.ListData;

@Controller
@RequestMapping(value="/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@RequestMapping(value="memberUpdate", method=RequestMethod.POST)
	public String update(MemberDTO memberDTO, RedirectAttributes rd, HttpSession session){
		String messege="Fail";
		try {
			int result = memberService.update(memberDTO, session);
			if(result>0){
				messege="Success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rd.addFlashAttribute("messege", messege);
		return "redirect:memberList";
	}
	
	@RequestMapping(value="memberUpdate", method=RequestMethod.GET)
	public String update(int num, Model model){
		try {
			MemberDTO memberDTO = memberService.selectOne(num);
			model.addAttribute("view", memberDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("member", "member");
		return "member/memberUpdate";
	}
	
	@RequestMapping(value="memberDelete")
	public String delete(int num, RedirectAttributes rd, HttpSession session)throws Exception{
		int result = 0;
		MemberDTO memberDTO = new MemberDTO();
		result = memberService.delete(num, memberDTO, session);
		
		String message = "Fail";
		if(result>0){
			message = "Success";
		}
		rd.addFlashAttribute("message", message);
		
		return "redirect:./memberList";
	}
	
	
	public String selectList(Model model, ListData listData)throws Exception{
		MemberDTO memberDTO = new MemberDTO();
		memberService.selectList(memberDTO, listData, model);
		model.addAttribute("member", "member");
		
		return "member/memberList";
	}
	@RequestMapping(value="memberView")
	public String selectOne(Model model, @RequestParam(defaultValue="0",required=false)int num)throws Exception{
		MemberDTO memberDTO = memberService.selectOne(num);
		model.addAttribute("view", memberDTO);
		model.addAttribute("member", "member");
		
		return "member/memberView";
	}
	
	@RequestMapping(value="memberWrite", method=RequestMethod.GET)
	public String insert(Model model){
		model.addAttribute("member", "member");
		return "member/memberWrite";
	}
	
	public String insert(int num,RedirectAttributes rd, MemberDTO memberDTO, HttpSession session)throws Exception{
		int result = 0;
		
		result = memberService.insert(memberDTO, session, num);
		
		String message = "fail";
		if(result>0){
			message="Success";
		}
		rd.addFlashAttribute("message", message);
		return "redirect:./memberList";
	}
}
