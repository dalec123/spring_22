package com.iu.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.member.MemberDTO;
import com.iu.util.DBConnector;

public class StudentDAO {
	//update
	public int update(StudentDTO studentDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "update student set tid=?, birth=?, grade=? where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, studentDTO.getTid());
		st.setString(2, studentDTO.getBirth());
		st.setInt(3, studentDTO.getGrade());
		st.setString(4, studentDTO.getId());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
		
	}
	
	
	
	
	//delete
	public int delete(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="delete student where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	
	
	
	//selectOne
	public StudentDTO selectOne(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select * from student where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		ResultSet rs = st.executeQuery();
		StudentDTO studentDTO = null;
		if(rs.next()){
			studentDTO = new StudentDTO();
			studentDTO.setTid(rs.getString("tid"));
			studentDTO.setBirth(rs.getString("birth"));
			studentDTO.setGrade(rs.getInt("grade"));
		}
		DBConnector.disConnect(rs, st, con);
		return studentDTO;
	}
}
