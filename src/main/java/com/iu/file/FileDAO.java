package com.iu.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.iu.util.DBConnector;

import oracle.jdbc.proxy.annotation.Pre;

@Repository
public class FileDAO {
	//selectList
	public List<FileDTO> selectList(int num)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="select * from upload where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		List<FileDTO> ar = new ArrayList<FileDTO>();
		FileDTO fileDTO=null;
		while(rs.next()){
			fileDTO = new FileDTO();
			fileDTO.setNum(rs.getInt("num"));
			fileDTO.setFnum(rs.getInt("fnum"));
			fileDTO.setFilename(rs.getString("filename"));
			fileDTO.setOriname(rs.getString("oriname"));
			ar.add(fileDTO);
		}
		DBConnector.disConnect(rs, st, con);
		
		return ar;
				
	}
	
	//insert
	public int insert(FileDTO fileDTO) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="insert into upload values(board_seq.nextval,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, fileDTO.getNum());
		st.setString(2, fileDTO.getFilename());
		st.setString(3, fileDTO.getOriname());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	//delete
	public int delete(int num) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="delete upload where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	//deleteOne
	public String selectOne(int fnum)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="select filename from upload where fnum=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, fnum);
		ResultSet rs = st.executeQuery();
		rs.next();
		String filename = rs.getString(1);
		DBConnector.disConnect(rs, st, con);
		return filename;
	}
	public int deleteOne(int fnum)throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="delete upload where fnum=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, fnum);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	

}
