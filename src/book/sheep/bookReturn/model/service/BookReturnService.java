package book.sheep.bookReturn.model.service;

import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.commit;
import static book.sheep.common.JdbcTemplate.getConnection;
import static book.sheep.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookReturn.model.dao.BookReturnDao;
import book.sheep.bookReturn.model.entity.BookReturn;
import book.sheep.member.model.entity.Member;

public class BookReturnService {

	BookReturnDao bookReturnDao = new BookReturnDao();
	
	public int insertBookReturn(BookRental bookRental) 
	{
		int result = 0;
		Connection conn = getConnection();
		try {
			result = bookReturnDao.insertBookReturn(conn, bookRental);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	//1109 
	public List<BookReturn> returnFindById(String member_id) 
	{
		Connection conn = getConnection();
		List<BookReturn> bookReturn = bookReturnDao.returnFindById(conn,member_id);
		close(conn);
		return bookReturn;
	}

}
