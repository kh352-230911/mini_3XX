package book.sheep.book.model.service;


import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.getConnection;

import java.sql.Connection;
import java.util.List;


import book.sheep.book.model.dao.BookDao;
import book.sheep.book.model.entity.Book;

/**
 * 2023-11-05
 * @author user
 *
 * bookDao에게 인자전달
 * connection, commit, rollback 처리단
 * bookDao로부터 받은 결과값을 다시 controller에게 리턴
 * 
 * common-JdbcTemplate클래스 임포트 방법:
 * window-preference-java-editor-content Assist-favorites에서 해당 클래스 찾아서 추가
 * 
 */
public class BookService 
{
	private BookDao bookDao = new BookDao();
	
	//모든 도서를 찾는 메소드
	public List<Book> findAllBook() {
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Book> bookList = bookDao.findAllBook(conn);
		// 3. 자원반납
		close(conn);
		return bookList;
	}
	
	/**
	 * 1106 책 제목으로 도서 검색하기.
	 */
	public List<Book> findBookByTitle(String bookTitle) {
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Book> bookList = bookDao.findBookByTitle(conn,bookTitle);
		// 3. 자원반납
		close(conn);
		return bookList;
	}
	/**
	 * 1106 책 저자명으로 도서 검색하기.
	 */
	public List<Book> findBookByAuthor(String bookAuthor) 
	{
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Book> bookList = bookDao.findBookByAuthor(conn,bookAuthor);
		// 3. 자원반납
		close(conn);
		return bookList;
	}
	/**
	 * 1107 책 출판사명으로 도서 검색하기.
	 */
	public List<Book> findBookByPublisher(String bookPublisher) 
	{
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Book> bookList = bookDao.findBookByPublisher(conn,bookPublisher);
		// 3. 자원반납
		close(conn);
		return bookList;
	}

	/**
	 * 
	 * 1107 도서 추가
	 */
	public int insertBook(Book book) 
	{
		//System.out.println("bookService - insertBook");
		Connection conn = getConnection();
		int result = bookDao.insertBook(conn,book);
		close(conn);
		return result;
	}
	
	/**
	 * 1107 도서 삭제
	 * 
	 * 
	 */
	public int deleteBook(Book book) 
	{
		//System.out.println("bookService - deleteBook");
		Connection conn = getConnection();
		int result = bookDao.deleteBook(conn,book);
		close(conn);
		return result;
	}
}
