package com.iu.board;

import java.util.List;

public interface boardDAO {

	//totalCount
	public int getTotalCount(String kind, String search) throws Exception;
	
	//update
	public int update(boardDTO boardDTO) throws Exception;
	
	//delete
	public int delete(int num)throws Exception;
	
	//hitUpdate
	public int hitUpdate(int num)throws Exception;
	
	//insert
	public int insert(boardDTO boardDTO) throws Exception;
	
	//selectOne
	public boardDTO selectOne(int num) throws Exception;
	
	//selectList
	public List<boardDTO> selectList()throws Exception;
}
