package com.iu.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Component;

import com.iu.board.BoardDAO;
import com.iu.board.BoardDTO;
import com.iu.notice.NoticeDTO;
import com.iu.util.DBConnector;
import com.iu.util.RowNum;
@Component
public class QnaDAO implements BoardDAO {

	private int replyUpdate(QnaDTO qnaDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="update qna set step=step+1 where ref=? and step>?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, qnaDTO.getRef());
		st.setInt(2, qnaDTO.getStep());
		int result = st.executeUpdate();
		return result;
	}
	
	//reply
	public int reply(QnaDTO qnaDTO)throws Exception {
		QnaDTO parent=(QnaDTO)this.selectOne(qnaDTO.getNum());
		int result=this.replyUpdate(parent);
		
		Connection con = DBConnector.getConnect();
		String sql ="insert into qna values(?,?,?,?,sysdate,0,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, qnaDTO.getTitle());
		st.setString(2, qnaDTO.getWriter());
		st.setString(3, qnaDTO.getContents());
		st.setInt(4, parent.getRef());
		st.setInt(5, parent.getStep()+1);
		st.setInt(6, parent.getDepth()+1);
		
		result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
		
	}
	
	
	
	
	
	
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
	public int getTotalCount(RowNum rowNum) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select nvl(count(num), 0) from qna where "+rowNum.getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+rowNum.getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		int totalCount = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return totalCount;
		
	}

	@Override
	public int update(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="update qna set title=?, contents=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setInt(3, boardDTO.getNum());
		
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int delete(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="delete qna where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int hitUpdate(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="update qna set hit=hit+1 where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int insert(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="insert into qna values(?,?,?,?,sysdate,0,0,0,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getWriter());
		st.setString(3, boardDTO.getTitle());
		st.setString(4, boardDTO.getContents());
		int result=st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="select * from qna where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		QnaDTO qnaDTO = null;
		if(rs.next()){
			qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
		}
		DBConnector.disConnect(rs, st, con);
		return qnaDTO;
		
	}

	@Override
	public List<BoardDTO> selectList(RowNum rowNum) throws Exception {
		
		
		
		Connection con = DBConnector.getConnect();
		String sql="select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where "+rowNum.getKind()+" like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+rowNum.getSearch()+"%");
		st.setInt(2, rowNum.getStartRow());
		st.setInt(3, rowNum.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		List<BoardDTO> ar = new ArrayList<BoardDTO>();
		
		while(rs.next()){
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			
			ar.add(qnaDTO);
		}
		
		DBConnector.disConnect(rs, st, con);
		return ar;
	}

	

}
