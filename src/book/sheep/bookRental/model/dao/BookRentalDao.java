package book.sheep.bookRental.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import book.sheep.book.model.entity.Book;
import book.sheep.book.model.exception.BookException;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookRental.model.exception.BookRentalException;
import book.sheep.bookRental.model.vo.BookRentalMember;
import book.sheep.bookRental.model.vo.BookRentalPart;
import book.sheep.bookRequest.model.exception.BookRequestException;
import book.sheep.notice.model.entity.Notice;

public class BookRentalDao 
{
	Properties prop = new Properties();
	
	//생성자를 호출할 때 prop을 미리 read한다.
	public BookRentalDao() 
	{
		try {
			prop.load(new FileReader("resources/book-rental-query.properties"));
			//System.out.println(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int insertBookRental(Connection conn,String member_id, Book book) 
	{
		int result=0;
		String sql = prop.getProperty("insertBookRental");
		
		try 
		(PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			//cstmt.registerOutParameter(1, Types.INTEGER); //result
			pstmt.setString(1, member_id); //회원아이디
			pstmt.setInt(2, book.getBook_no()); //책번호
			pstmt.setString(3, book.getBook_title()); //책제목
			
			//pstmt.execute(); //1107 두번실행해서 두번들어감 아놔ㅡㅡ
			result = pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new BookRentalException("대여 신청 오류",e);
		}
		return result;
	}
	
	/**
	 * 1108 사용자 아이디로 tb_book_rental에서 대여한 내역을 확인하는 메소드
	 * findAllMyBookRental = select * from tb_book_rental where member_id=?
	 */
	
	public List<BookRental> findAllMyBookRental(Connection conn,String id) 
	{
		List<BookRental> bookrentals = new ArrayList<>();
		String sql = prop.getProperty("findAllMyBookRental"); 
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
						bookrentals.add(handleBookRentalResultSet(rset));//리스트에 추가한다.
					}
				}
				//4.반환(resultSet, PreparedStatement)
				//위에 try resource구문에서 선언하였으므로 반환은 자동.
			} 
		catch (SQLException e) 
		{
			throw new BookException("나의 대여 조회 오류!", e);
		}
		return bookrentals;
	}
	
	/**
	 *  private int rental_no;
		private String member_id;
		private String book_no;
		private String book_title;
		private Date rental_start;
		private Date rental_end;
		private boolean book_is_rental; //얘는 안가져와도 될듯
		private boolean rental_state;
	 * @param rset
	 * @return
	 * @throws SQLException
	 */
	private BookRental handleBookRentalResultSet(ResultSet rset) throws SQLException 
	{
		BookRental bookRental = new BookRental();
		//tb_book_rental의 값을 가져와야함.
		bookRental.setRental_no(rset.getInt("rental_no")); 
		bookRental.setMember_id(rset.getString("member_id"));
		bookRental.setBook_no(rset.getInt("book_no"));
		bookRental.setBook_title(rset.getString("book_title"));
		bookRental.setRental_start(rset.getDate("rental_start"));
		bookRental.setRental_end(rset.getDate("rental_end"));
		bookRental.setRental_state(rset.getInt("book_is_rental")==1);//반납유무를 따지는 boolean
		
		return bookRental;
	}
	
	/**
	 * 1108 도서 대여 내역 삭제
	 */
	public int deleteBookRental(Connection conn, BookRental bookRental) 
	{
		//System.out.println("빌린 책 삭제dao");
		int result = 0;
		String sql = prop.getProperty("deleteBookRental");
		//1.PreparedStatement 객체 생성 (sql) - 미완성 쿼리 값 대입
		try(
				PreparedStatement pstmt = conn.prepareStatement(sql);
				)
		{
			//2.쿼리 실행 pstmt.executeUpdate():int
			System.out.println("삭제할 대여 번호:_"+bookRental.getRental_no());
			pstmt.setInt(1, bookRental.getRental_no());
			result = pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//쿼리 실행시 익셉션 발생시 throw
			throw new BookException("대여 내역 삭제 오류 발생",e);
		}
		//3.자원반납(pstmt) - 각 클래스에서 생성한 것만 반환 
		//try resource라 자동 할당해제
		return result;
	}
	
	/**
	 * 
	 */
	
	//---------------------------------------
			//11.09 - 2. 관리자가 회원정보 조회시. 등록된 모든 회원정보+책대여정보를 출력해주는 로직 (배성은)
			//모든 회원정보를 출력하므로, 결과값은 LIST여야함.  ??
			public List<BookRentalMember> findAllBookRentalMember(Connection conn) {
				List<BookRentalMember>  bookRentalMemberList = new ArrayList<>();
				
				//2.1포로퍼티에서 작성한 쿼리문중 읽어올 쿼리문 이름 등록->그러면 이름 하위에 작성된 쿼리문의 내용을 가져와준다.
				  // ->멤버 테이블의 모든 회원정보+도서대여정보 가져오는 쿼리를 실행하자
				// sql 변수에 select문이 대입됨!
				String sql = prop.getProperty("findmemberRental");
				
				//2.2 PreparedStatement 생성해 select문을 대입한다. 
				try (PreparedStatement pstmt = conn.prepareStatement(sql)){
					
					//2.3쿼리 실행 : pstmt. executeQuery():쿼리문 실행! ,쿼리문 실행한 그 결과값을 Result set에 담는다.
				    try(ResultSet rset = pstmt.executeQuery()) {
				      //2.4 result set의 결과를 쪼개서, 자바의 필드값에 컬럼값을 기입.
				      while (rset.next()) { //포인터가 다음 row가 있는지 확인해 T/F 반환
				    	 
				    	  //DB에서 따온 결과값을 BookRentalMember 필드에 저장하고 그걸 객체로 생성(상위의 모든 과정은 한행씩 처리됨 )
				    	  BookRentalMember bookRentalMember = handleMemberResultSet2Test(rset);
				    	  //한개의 row값을 이제 LIST에 담는다. 우린 전체내용이 필요하니까!
				    	  bookRentalMemberList.add(bookRentalMember); 
				      }//while	
				    } //try2	
					
				} catch (SQLException e) {
				   throw new BookRequestException("회원정보 조회 오류!", e);
				}
				//2.5 member 객체를 LIST에 담아 반환. 어디로? service class로
				return bookRentalMemberList ;
			}
			
			
			
			//11.09 TEST용 -매개인자로 받은 쿼리문의 result set의 결과를(아직 DB단계에서만 정보 처리된 상태) 
			       //DB에서 회원의 이름/아이디/휴대폰 번호 + 대출한 도서정보(도서이름,) 가져오는 메서드 (배성은)
			private BookRentalMember handleMemberResultSet2Test(ResultSet rset) throws SQLException {
				BookRentalMember bookRentalMember = new BookRentalMember();
				//회원 정보 : 이름, 아이디, 전화번호
				bookRentalMember.setMember_name(rset.getString("member_name"));
				bookRentalMember.setMember_id(rset.getString("member_id"));
				bookRentalMember.setMember_phone(rset.getString("member_phone"));
				//도서 대여 정보: 도서NO, 도서이름, 반납예정일 ; 한 사람당 여려권을 책을 대여했을수 있음
				if(rset.getInt("book_no") != 0) 
				{
					do {
						BookRentalPart br = new BookRentalPart();
					    br.setBook_no(rset.getInt("book_no"));	
					    br.setBook_title(rset.getString("book_title"));
					    br.setRental_end(rset.getDate("rental_end"));
					    bookRentalMember.addBookRental(br);
					}
					while(rset.next());
				}
				return bookRentalMember;
			}

}
