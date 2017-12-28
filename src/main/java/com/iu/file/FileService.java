package com.iu.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.util.FileSaver;

@Service
public class FileService {

	private FileSaver fileSaver;
	@Autowired
	private FileDAO fileDAO;
	
	
	public int fileDelete(int fnum, HttpSession session)throws Exception{
		String fileName=fileDAO.selectOne(fnum);
		int result = fileDAO.deleteOne(fnum);
		String filePath = session.getServletContext().getRealPath("resources/upload");
		File file = new File(filePath, fileName);
		file.delete();
		return result;
	}
	
	
	public List<String> fileSave(MultipartFile [] f1, HttpSession session) throws Exception{
		String filePath = session.getServletContext().getRealPath("resources/upload");
		ArrayList<String> names=new ArrayList<String>();
		
		for(MultipartFile f: f1){
			String fileName=fileSaver.save2(filePath, f);
			names.add(fileName);
		}
		
		return names;
		
	}
	
}

