package book.sheep.bookReturn.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import book.sheep.book.model.exception.BookException;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookReturn.model.entity.BookReturn;
import book.sheep.bookReturn.model.exception.BookReturnException;
import book.sheep.member.model.entity.Member;
import book.sheep.member.model.exception.MemberException;
import book.sheep.notice.model.exception.NoticeException;

public class BookReturnDao 
{

	private Properties prop = new Properties();
	public BookReturnDao() 
	{
		try 
		{
			prop.load(new FileReader("resources/book-return-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1108
	 * 대여했던 값을 return table에 insert 메소드
	 * insert into tb_book_return values(seq_book_return_no.nextval,?,default);
	 * insertBookReturn
	**/
	public int insertBookReturn(Connection conn, BookRental bookRental) 
	{
		int result=0;
		String sql = prop.getProperty("insertBookReturn");
		
		try 
		(PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			pstmt.setInt(1, bookRental.getRental_no());
			result = pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new BookReturnException("반납 추가 오류",e);
		}
		return result;
	}

	/**
	 * 1109 사용자 아이디로 반납처리된 내역 select
	 */
	public List<BookReturn> returnFindById(Connection conn,String id) 
	{
		List<BookReturn> bookReturn = new ArrayList<>();
		String sql = prop.getProperty("returnFindById"); 
		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try(
				PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1,id);
				//2.쿼리 실행 pstmt.executeQuery():resultSet
				try(
						ResultSet rset = pstmt.executeQuery())
				{
					//3.resultSet 처리
					while(rset.next())
					{
						bookReturn.add(handleBookReturnResultSet(rset));//리스트에 추가한다.
					}
				}
				//4.반환(resultSet, PreparedStatement)
				//위에 try resource구문에서 선언하였으므로 반환은 자동.
			} 
		catch (SQLException e) 
		{
			throw new BookReturnException("나의 반납 조회 오류!", e);
		}
		return bookReturn;
	}
	
	

	//책 번호는 제외함
	private BookReturn handleBookReturnResultSet(ResultSet rset) throws SQLException 
	{
		BookReturn bookReturn = new BookReturn();
		bookReturn.setReturn_no(rset.getInt("return_no"));
		bookReturn.setMember_id(rset.getString("member_id"));
		bookReturn.setBook_title(rset.getString("book_title"));
		
		bookReturn.setRental_start(rset.getDate("rental_start"));
		bookReturn.setRental_end(rset.getDate("rental_end"));
		bookReturn.setReturn_date(rset.getDate("return_date"));
		return bookReturn;
	}

}
