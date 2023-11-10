package book.sheep.manager.model.dao;

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
import book.sheep.manager.model.entity.Manager;
import book.sheep.manager.model.exception.ManagerException;

/**
 * 2023-11-05
 * @author 고혜진
 * db와 직접 access하는 단 (Data Access Object)
 * 이 클래스에서 쿼리를 실행하고, 결과값을 반환한다.
 */
public class ManagerDao {

Properties prop = new Properties();
	
	//생성자를 호출할 때 prop을 미리 read한다.
	public ManagerDao() 
	{
		try {
			prop.load(new FileReader("resources/manager-query.properties"));
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
	private Book handleBookResultSet(ResultSet rset) throws SQLException {
		Book book = new Book();
		book.setBook_no(rset.getInt("book_no")); //책번호
		book.setBook_title(rset.getString("book_title"));//책제목
		book.setBook_author(rset.getString("book_author"));//책저자
		book.setBook_type(rset.getInt("book_type"));//책 타입
		book.setSame_book_count(rset.getInt("same_book_count"));//동일 권수
		book.setBook_is_rental(rset.getInt("book_is_rental")==1);//대여 가능 여부
		return book;
	}

	//입력한 id와 password로 tb_manager에서 일치하는 결과행을 찾아 manager객체로 생성
	//managerLogin = select *from tb_manager where manager_id=? and manager_pw=?
	public Manager managerLogin(Connection conn, String id, String password) 
	{
		String sql = prop.getProperty("managerLogin");
		Manager manager = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					manager = new Manager();
					// 회원관련
					manager.setManager_id(rset.getString("manager_id"));
					manager.setManager_pw(rset.getString("manager_pw"));
					manager.setIs_manager(rset.getInt("is_manager")==1);
					//manager.setManager_no(rset.getInt("manager_no"));
				}
			}
		} catch (SQLException e) {
			throw new ManagerException("관리자 로그인 오류!", e);
		}
	
		return manager;
	}
	
}
