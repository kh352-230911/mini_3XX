package book.sheep.bookRental.model.service;

import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.commit;
import static book.sheep.common.JdbcTemplate.getConnection;
import static book.sheep.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import book.sheep.book.model.entity.Book;
import book.sheep.bookRental.model.dao.BookRentalDao;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookRental.model.vo.BookRentalMember;
import book.sheep.bookRental.model.vo.BookRentalPart;
import book.sheep.bookRequest.model.exception.BookRequestException;

public class BookRentalService {

	private BookRentalDao bookRentalDao = new BookRentalDao();

	public int insertBookRental(String member_id,Book book) 
	{
		int result = 0;
		Connection conn = getConnection();
		try {
			result = bookRentalDao.insertBookRental(conn, member_id,book);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	/**
	 * 1108 회원의 대여내역 조회
	 * findAllMyBookRental
	 */
	public List<BookRental> findAllMyBookRental(String id) {
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<BookRental> bookRentals = bookRentalDao.findAllMyBookRental(conn,id);
		// 3. 자원반납
		close(conn);
		return bookRentals;
	}

	/**
	 * 대여 내역 삭제[반납처리]
	 */
	public int deleteBookRental(BookRental bookRental) 
	{
		//System.out.println("빌린 책 삭제service");
		Connection conn = getConnection();
		int result = bookRentalDao.deleteBookRental(conn,bookRental);
		close(conn);
		return result;
	}

	//11.09 (멤버이름,아이디,휴대폰번호,대여한 책이름,책넘버,반납일) 모든 레이블 출력하는 메서드,
    public List<BookRentalMember> findAllBookRentalMember () {
    	Connection conn = getConnection();
		// 2.2 Dao 호출s
		List<BookRentalMember> bookRentalMember = bookRentalDao.findAllBookRentalMember(conn);
		// 2.3 자원반납
		close(conn);
		return bookRentalMember ;
	
    } 	
	
    
    
    
    
}
