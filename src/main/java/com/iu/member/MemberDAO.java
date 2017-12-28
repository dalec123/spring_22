package com.iu.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.util.DBConnector;
import com.iu.util.RowNum;

public class MemberDAO {
	//total
	public int getTotalCount(RowNum rowNum) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "select nvl(count(*), 0) from notice where "+rowNum.getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+rowNum.getSearch()+"%");

		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		int totalCount = rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		
		return totalCount;
	}
	
	//hit
	public int hitUpdate(int num) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "update notice set hit=hit+1 where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}

	
	
	//num
	public int getNum() throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="select board_seq.nextval from dual";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		int num = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return num;
	}
	
	
	//idcheck
	public boolean idCheck(String id)throws Exception{
		boolean check = true;
		Connection con = DBConnector.getConnect();
		String sql = "select * from member where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			check =false;
		}
		DBConnector.disConnect(rs, st, con);
		return check;
	}
	
	
	//update
	public int update(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "update member set pw=?, name=?, age=?, phone=?, where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memberDTO.getPw());
		st.setString(2, memberDTO.getName());
		st.setInt(3, memberDTO.getAge());
		st.setString(4, memberDTO.getPhone());
		st.setString(5, memberDTO.getId());
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	
	
	//delete
	public int delete(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="delete member where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
		
	}
	
	
	
	
	//insert
	public int insert(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="insert into member values(?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		st.setString(3, memberDTO.getName());
		st.setString(4, memberDTO.getPhone());
		st.setInt(5, memberDTO.getAge());
		st.setString(6, memberDTO.getJob());
		
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	//selectOne
	public MemberDTO selectOne(MemberDTO memberDTO)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="select * from member where id=? and pw=? and job=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		st.setString(3, memberDTO.getJob());
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			memberDTO.setName(rs.getString("name"));
			memberDTO.setAge(rs.getInt("age"));
			memberDTO.setPhone(rs.getString("phone"));
		}else{
			memberDTO=null;
		}
		DBConnector.disConnect(rs, st, con);
		return memberDTO;
	}
}
