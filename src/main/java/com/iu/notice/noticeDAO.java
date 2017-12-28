package com.iu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.iu.board.BoardDAO;
import com.iu.board.BoardDTO;
import com.iu.file.FileDTO;
import com.iu.util.DBConnector;
import com.iu.util.RowNum;

@Repository
public class NoticeDAO implements BoardDAO{
	
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

	@Override
	public int insert(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "insert into notice values(?,?,?,?,sysdate,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getWriter());
		st.setString(3, boardDTO.getTitle());
		st.setString(4, boardDTO.getContents());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	

	@Override
	public int update(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "update notice set title=?, contents=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setInt(3, boardDTO.getNum());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}

	@Override
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

	@Override
	public int delete(int num) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "delete notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}

	@Override
	public List<BoardDTO> selectList(RowNum rowNum) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "select * from"
				+ " (select rownum R, N.* from"
				+ " (select * from notice where "+rowNum.getKind()+" like ? order by num desc) N)"
				+ " where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+rowNum.getSearch()+"%");
		st.setInt(2, rowNum.getStartRow());
		st.setInt(3, rowNum.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		List<BoardDTO> ar = new ArrayList<BoardDTO>();
		while(rs.next()){
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);
		}
		
		DBConnector.disConnect(rs, st, con);
		
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBConnector.getConnect();
		String sql = "select * from notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, num);
		
		ResultSet rs = st.executeQuery();
		
		NoticeDTO noticeDTO = null;
		if(rs.next()){
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		
		DBConnector.disConnect(rs, st, con);
		
		return noticeDTO;
	}

	@Override
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


}
