package com.iu.board;

import java.util.List;

public interface BoardDAO {

	//totalCount
	public int getTotalCount(String kind, String search) throws Exception;
	
	//update
	public int update(BoardDTO boardDTO) throws Exception;
	
	//delete
	public int delete(int num)throws Exception;
	
	//hitUpdate
	public int hitUpdate(int num)throws Exception;
	
	//insert
	public int insert(BoardDTO boardDTO) throws Exception;
	
	//selectOne
	public BoardDTO selectOne(int num) throws Exception;
	
	//selectList
	public List<BoardDTO> selectList()throws Exception;
	
	public int HitUpdate(int num)throws Exception;
	
	
}
