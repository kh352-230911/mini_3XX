package book.sheep.bookRequest.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import book.sheep.bookRequest.model.entity.BookRequest;
import book.sheep.bookRequest.model.exception.BookRequestException;
import book.sheep.common.LoginTemplate;

public class BookRequestDao {

	Properties prop = new Properties(); //properties 클래스 import필요 
	
	//이건 기능이 뭐지?
	public BookRequestDao () {
		 try {
			//fileReafer 클래스 import하고, 읽어오려는 파일이 존재하지 않을수 있으니,
			 //try catch 처리.
			prop.load(new FileReader("resources/bookRequest-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//dao괄호
	
	//1.insert 메서드.
	public int insertBookRequest (Connection conn, BookRequest bookRequest) {
		String sql = prop.getProperty("insertBookRequest");
		int result = 0;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			LoginTemplate lt = new LoginTemplate();
			String id = null;
			for (String key : lt.ReadUser().keySet()) {
				  System.out.println(key);
				  id = key;
				  }
		   pstmt.setString(1, id);
		   pstmt.setString(2, bookRequest.getReqeustBookTitle());
		   pstmt.setString(3, bookRequest.getReqeustBookAuthor());
		   pstmt.setString(4, bookRequest.getReqeustBookPublisher());
		   
		  // 2. 쿼리실행 pstmt.executeUpdate():int		
		  result = pstmt.executeUpdate();	
		} catch(SQLException e) {
			//unchecked 예외로 전환 후 던지기 필요.
			throw new BookRequestException("도서요청 오류!", e);
		}
	  return result;
	}//insert 메서드 괄호
	
	
	
	//2.select * 메서드
	public List<BookRequest> findAllBookRequest(Connection conn) {
		List<BookRequest> bookRequestList = new ArrayList<>();
		
		//2.1포로퍼티에서 작성한 쿼리문중 읽어올 쿼리문 이름 등록
		String sql = prop.getProperty("findAllBookRequest");
		
		//2.2 PreparedStatement 생성 & 미완성쿼리 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			//2.3쿼리 실행! 왜 트라이문으로 감싸지?
		    try(ResultSet rset = pstmt.executeQuery()) {
		      //2.4 result set을 처리하기.
		      while (rset.next()) {
		    	  BookRequest bookRequest = handleBookRequestResultSet(rset);
		    	  bookRequestList.add(bookRequest); 
		    	  
		      }	
		    } 	
			
		} catch (SQLException e) {
		   throw new BookRequestException("도서 요청 전체조회 오류!", e);
		}
		return bookRequestList ;
	}
	
	
	
	//3.BookRequest 결과 가져오는 메서드
	private BookRequest handleBookRequestResultSet(ResultSet rset) throws SQLException {
		BookRequest br = new BookRequest();
		br.setReqeustNo(rset.getInt("request_no"));   
		br.setMemberId(rset.getString("member_id"));
		br.setReqeustBookTitle(rset.getString("request_book_title")); 
		br.setReqeustBookAuthor(rset.getString("request_book_author"));  
		br.setReqeustBookPublisher(rset.getString("request_book_publisher"));
		
		//-----------------
		//System.out.println(br);
		return br;
	}
	
	 public int deleteBookRequest(Connection conn, int requestNo) {
		    String sql = prop.getProperty("deleteBookReQuest");
		    int result = 0;
				
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setInt(1,requestNo );
					result = pstmt.executeUpdate();
				} catch (SQLException e) {
					throw new BookRequestException("도서 요청 삭제 오류!", e);
				}
				return result;
		  } 
	
	
	
}//class괄호
