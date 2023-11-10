package book.sheep.notice.model.service;

import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.getConnection;

import java.sql.Connection;
import java.util.List;


import book.sheep.book.model.dao.BookDao;
import book.sheep.book.model.entity.Book;
import book.sheep.notice.model.dao.NoticeDao;
import book.sheep.notice.model.entity.Notice;

public class NoticeService 
{
	private NoticeDao noticeDao = new NoticeDao();
	//공지 전체를 가져옴
	public List<Notice> findAllNotice() 
	{
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Notice> notices = noticeDao.findAllNotice(conn);
		// 3. 자원반납
		close(conn);
		return notices;
	}
	
	//1106 jin 공지 추가 구현
	public int insertNotice(Notice notice) 
	{
		//System.out.println("NoticeService-insertNotice");
		Connection conn = getConnection();
		int result = noticeDao.insertNotice(conn,notice);
		close(conn);
		return result;
	}

	//1107 jin 공지 삭제 구현
	public int deleteNotice(Notice notice) 
	{
		//System.out.println("NoticeService-deleteNotice");
		Connection conn = getConnection();
		int result = noticeDao.deleteNotice(conn,notice);
		close(conn);
		return result;
	}
	
}
