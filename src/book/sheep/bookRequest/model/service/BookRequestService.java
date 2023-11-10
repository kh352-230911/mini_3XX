package book.sheep.bookRequest.model.service;
// Static 자원들 import 하기 (close, commit, get connection, rollback)
import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.commit;
import static book.sheep.common.JdbcTemplate.getConnection;
import static book.sheep.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import book.sheep.bookRequest.model.dao.BookRequestDao;
import book.sheep.bookRequest.model.entity.BookRequest;

//service 클래스의 메서드는 결과값을 컨트롤 클래스로 반환해야함. 
public class BookRequestService {
  
	// BookRequestDao 객체 생성필요.
     private BookRequestDao bookRequestDao =
    		 new BookRequestDao();
	
	
	//1. insert하는 메서드, insert는 한행씩만 진행되니까? 그냥 객체가 매개인자이고,
	//insert하려는 뭐시기가 1이면 반환???
	public int insertBookRequest (BookRequest bookRequest) {
	  int result = 0;
	  //1.1 Connection 생성
	  Connection conn = getConnection(); 
	  
	  try {
	  //1.2 Dao에 요청
	  result = bookRequestDao.insertBookRequest(conn, bookRequest);
	  //1.3 트랜잭션처리
	  commit(conn);
	  } catch (Exception e) {
		  rollback(conn);
		  throw e;
	  } finally {  
	  // 1.4 반드시, 자원반납하기(conn)
	    close(conn);
	  }
	  return result;
	}

     //2. 모든 도서요청정보를 select
	public List<BookRequest> findAllBookRequest() {
		// 2.1 Connection 생성
		Connection conn = getConnection();
		// 2.2 Dao 호출s
		List<BookRequest> bookRequests = bookRequestDao.findAllBookRequest(conn);
		// 2.3 자원반납
		close(conn);
		return bookRequests ;
	}

	public int deleteBookRequest(int requestNo) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = bookRequestDao.deleteBookRequest(conn, requestNo);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

} //class 괄호
