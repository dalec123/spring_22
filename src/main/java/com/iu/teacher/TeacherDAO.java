package com.iu.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iu.member.MemberDTO;
import com.iu.student.StudentDTO;
import com.iu.util.DBConnector;
import com.iu.util.Pager;
import com.iu.util.RowNum;

public class TeacherDAO {

	public int getTotalCount(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select count(nvl(id,0)) from student where tid=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		return result;
	}
	
	
	
	
	
	//list
	public List<StudentDTO> studentInfo(MemberDTO memberDTO, RowNum rowNum)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select * from \r\n" + 
				"(select rownum R, st.* from\r\n" + 
				"(select M.id, M.name, M.phone, S.grade, S.birth from MEMBER M, STUDENT S\r\n" + 
				"where M.id = S.ID and S.TID='choa' order by M.id asc) st)\r\n" + 
				"where R between 1 and 10";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setInt(2, rowNum.getStartRow());
		st.setInt(3, rowNum.getLastRow());
		ResultSet rs = st.executeQuery();
		ArrayList<StudentDTO> ar = new ArrayList<StudentDTO>();
		while(rs.next()){
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setId(rs.getString("id"));
			studentDTO.setName(rs.getString("name"));
			studentDTO.setPhone(rs.getString("phone"));
			studentDTO.setGrade(rs.getInt("grade"));
			studentDTO.setBirth(rs.getString("birth"));
			ar.add(studentDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}
	
	
	
	
	
	
	//update
	public int update(TeacherDTO teacherDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "update teahcer set hiredate=?, subject=?, sal=? where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setDate(1, teacherDTO.getHiredate());
		st.setString(2, teacherDTO.getSubject());
		st.setInt(3, teacherDTO.getSal());
		st.setString(4, teacherDTO.getId());
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	
	
	
	
	
	//delete
	public int delete(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "delete teacher where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	
	
	
	//insert
	public int insert(TeacherDTO teacherDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "insert into teacher values(?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, teacherDTO.getId());
		st.setDate(2, teacherDTO.getHiredate());
		st.setString(3, teacherDTO.getSubject());
		st.setInt(4, teacherDTO.getSal());
		
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	
	
	//selectOne
	public TeacherDTO selectOne(MemberDTO memberDTO)throws Exception{
		Connection con  = DBConnector.getConnect();
		String sql = "select * from teacher where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memberDTO.getId());
		ResultSet rs = st.executeQuery();
		TeacherDTO teacherDTO = null;
		if(rs.next()){
			teacherDTO = new TeacherDTO();
			teacherDTO.setHiredate(rs.getDate("hiredate"));
			teacherDTO.setSubject(rs.getString("subject"));
			teacherDTO.setSal(rs.getInt("sal"));
		}
		DBConnector.disConnect(rs, st, con);
		return teacherDTO;
	}
}
