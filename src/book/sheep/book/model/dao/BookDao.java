package book.sheep.book.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import book.sheep.book.model.entity.Book;
import book.sheep.book.model.exception.BookException;
import book.sheep.notice.model.exception.NoticeException;

/**
 * 2023-11-05
 * @author 고혜진
 * db와 직접 access하는 단 (Data Access Object)
 * 이 클래스에서 쿼리를 실행하고, 결과값을 반환한다.
 */
public class BookDao 
{
	Properties prop = new Properties();
	
	//생성자를 호출할 때 prop을 미리 read한다.
	public BookDao() {
		try {
			prop.load(new FileReader("resources/book-query.properties"));
			//System.out.println(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//도서 전체 조회(모두 나옴)
	
	public List<Book> findAllBook(Connection conn) {
		List<Book> bookList = new ArrayList<>();
		//properties의 key값은 메소드명과 일치하도록 작성합시다.
		String sql = prop.getProperty("findAllBook"); 
		
		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			// 2. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
			try(ResultSet rset = pstmt.executeQuery()){

				// 3. ResultSet 처리 : 행 -> dto객체
				while(rset.next()) {
					// 한행의 컬럼값을 가져와 Member객체로 변환 
					// 매개변수생성자 | 기본생성자 + setter
					Book book = handleBookResultSet(rset);
					//bookList에 추가
					bookList.add(book);
				}
			}
		} catch (SQLException e) {
			throw new BookException("도서 전체 조회 오류!", e);
		}
		
		
		return bookList;
	}
	private Book handleBookResultSet(ResultSet rset) throws SQLException 
	{
		Book book = new Book();
		book.setBook_no(rset.getInt("book_no")); //책번호
		book.setBook_title(rset.getString("book_title"));//책제목
		book.setBook_author(rset.getString("book_author"));//책저자
		book.setBook_publisher(rset.getString("book_publisher"));//출판사
		book.setBook_is_rental(rset.getInt("book_is_rental")==1);//대여 가능 여부
		return book;
	}

	//1106 jin 책 이름으로 검색. like연산자로 검색.
	public List<Book> findBookByTitle(Connection conn, String bookTitle) 
	{
		List<Book>bookList = new ArrayList<>();
		String sql = prop.getProperty("findBookByTitle");
		//1.PreparedStatement 객체 생성 (query) - 미완성 쿼리 값 대입
		try(
			PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1,"%"+bookTitle+"%");
			//2.쿼리 실행 pstmt.executeQuery():resultSet
			try(
					ResultSet rset = pstmt.executeQuery())
			{
				//3.resultSet 처리
				while(rset.next())
				{
					//Member member = handleMemberResultSet(rset);
					//members.add(member);
					
					//위의 두줄 코드 합쳐서
					bookList.add(handleBookResultSet(rset));//리스트에 추가한다.
				}
			}
			//4.반환(resultSet, PreparedStatement)
			//위에 try resource구문에서 선언하였으므로 반환은 자동.
		} 
		catch (SQLException e) 
		{
			//dao 캐치에서는 e 메시지 대신에 throw를..
			throw new BookException("책제목 도서 검색 오류!",e);
		}
		return bookList;
	}

	public List<Book> findBookByAuthor(Connection conn, String bookAuthor) {
		List<Book>bookList = new ArrayList<>();
		String sql = prop.getProperty("findBookByAuthor");
		//1.PreparedStatement 객체 생성 (query) - 미완성 쿼리 값 대입
		try(
			PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1,"%"+bookAuthor+"%");
			//2.쿼리 실행 pstmt.executeQuery():resultSet
			try(
					ResultSet rset = pstmt.executeQuery())
			{
				//3.resultSet 처리
				while(rset.next())
				{
					//Member member = handleMemberResultSet(rset);
					//members.add(member);
					
					//위의 두줄 코드 합쳐서
					bookList.add(handleBookResultSet(rset));//리스트에 추가한다.
				}
			}
			//4.반환(resultSet, PreparedStatement)
			//위에 try resource구문에서 선언하였으므로 반환은 자동.
		} 
		catch (SQLException e) 
		{
			//dao 캐치에서는 e 메시지 대신에 throw를..
			throw new BookException("저자명 도서 검색 오류!",e);
		}
		return bookList;
	}

	/**
	 * 1107 jin 출판사명으로 검색
	 */
	public List<Book> findBookByPublisher(Connection conn, String bookPublisher) 
	{
		List<Book>bookList = new ArrayList<>();
		String sql = prop.getProperty("findBookByPublisher");
		//1.PreparedStatement 객체 생성 (query) - 미완성 쿼리 값 대입
		try(
			PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1,"%"+bookPublisher+"%");
			//2.쿼리 실행 pstmt.executeQuery():resultSet
			try(
					ResultSet rset = pstmt.executeQuery())
			{
				//3.resultSet 처리
				while(rset.next())
				{
					//Member member = handleMemberResultSet(rset);
					//members.add(member);
					
					//위의 두줄 코드 합쳐서
					bookList.add(handleBookResultSet(rset));//리스트에 추가한다.
				}
			}
			//4.반환(resultSet, PreparedStatement)
			//위에 try resource구문에서 선언하였으므로 반환은 자동.
		} 
		catch (SQLException e) 
		{
			//dao 캐치에서는 e 메시지 대신에 throw를..
			throw new BookException("저자명 도서 검색 오류!",e);
		}
		return bookList;
	}

	/**
	 * 1107 도서 추가
	 *  book.setBook_title("먼작귀1");
		book.setBook_author("나가노");
		book.setBook_publisher("대원씨아이");
		book.setBook_type(1);
	 */
	public int insertBook(Connection conn, Book book) 
	{
		//System.out.println("bookDao - insertBook");
		int result=0;
		String sql = prop.getProperty("insertBook");
		
		try 
		(PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			pstmt.setString(1, book.getBook_title());
			pstmt.setString(2, book.getBook_author());
			pstmt.setString(3, book.getBook_publisher());
			//pstmt.setInt(4, book.getBook_type());
			
			result = pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new NoticeException("도서 추가 오류",e);
		}
		return result;
	}
	
	/**
	 * 1107 도서 삭제
	 * 쿼리: delete from tb_book_info where book_no = ? and book_is_rental = 1
	 * 조건 : 해당 book_no의 대여상태가 가능할때만 삭제한다.
	 * 이유 : 대여중인 상태에서 삭제할 수 없음. book_is_rental=0일시 삭제x
	 */
	
	public int deleteBook(Connection conn,Book book) 
	{
		//System.out.println("bookDao - deleteBook");
		int result = 0;
		String sql = prop.getProperty("deleteBook");
		//1.PreparedStatement 객체 생성 (sql) - 미완성 쿼리 값 대입
		try(
				PreparedStatement pstmt = conn.prepareStatement(sql);
				)
		{
			//2.쿼리 실행 pstmt.executeUpdate():int
			pstmt.setInt(1, book.getBook_no());
			result = pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//쿼리 실행시 익셉션 발생시 throw
			throw new BookException("도서 삭제 오류 발생",e);
		}
		//3.자원반납(pstmt) - 각 클래스에서 생성한 것만 반환 
		//try resource라 자동 할당해제
		return result;
	}
	
	
}
