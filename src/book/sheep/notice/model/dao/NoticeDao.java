package book.sheep.notice.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import book.sheep.book.model.entity.Book;
import book.sheep.book.model.exception.BookException;
import book.sheep.notice.model.entity.Notice;
import book.sheep.notice.model.exception.NoticeException;

public class NoticeDao 
{
	Properties prop = new Properties();
	//생성자를 호출할 때 prop을 미리 read한다.
	public NoticeDao() {
		try {
			prop.load(new FileReader("resources/notice-query.properties"));
			//System.out.println(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//1106 jin 모든 공지를 select하는 메소드
	public List<Notice> findAllNotice(Connection conn) {
		List<Notice> notices = new ArrayList<>();
		String sql = prop.getProperty("findAllNotice"); 
		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			// 2. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
			try(ResultSet rset = pstmt.executeQuery()){
				// 3. ResultSet 처리 : 행 -> dto객체
				while(rset.next()) {
					// 한행의 컬럼값을 가져와 Member객체로 변환 
					// 매개변수생성자 | 기본생성자 + setter
					Notice notice = handleNoticeResultSet(rset);
					//bookList에 추가
					notices.add(notice);
				}
			}
		} 
		catch (SQLException e) 
		{
			throw new BookException("공지사항 전체 조회 오류!", e);
		}
		return notices;
	}
	private Notice handleNoticeResultSet(ResultSet rset) throws SQLException 
	{
		Notice notice = new Notice();
		//tb_notice에서 필요한 컬럼값을 가져와야함. 공지번호pk/공지내용/관리자명/작성날짜만 가져오면 됨.
		notice.setNotice_no(rset.getInt("notice_no")); //공지 번호
		notice.setNotice_content(rset.getString("notice_content"));//공지 내용
		notice.setManager_id(rset.getString("manager_id"));//관리자명 
		notice.setNotice_date(rset.getDate("notice_date"));//작성날짜
		return notice;
	}


	//1106 jin 관리자 아이디로 공지사항을 추가하는 메소드
	public int insertNotice(Connection conn, Notice notice) 
	{
		System.out.println("NoticeDao-insertNotice");
		int result=0;
		String sql = prop.getProperty("insertNotice");
		
		try 
		(PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			//cstmt.registerOutParameter(1, Types.INTEGER); //result
			pstmt.setString(1, notice.getManager_id());
			pstmt.setString(2, notice.getNotice_content());
			pstmt.setString(3, notice.getNotice_pw());
			
			//pstmt.execute(); //1107 두번실행해서 두번들어감 아놔
			
			result = pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new NoticeException("공지 추가 오류",e);
		}
		return result;
	}

	//1107 jin 공지 삭제 구현
	//공지삭제에 필요한 값
	//delete from tb_notice where manager_id ='3XX' and notice_no =13 and notice_pw ='1111';
	public int deleteNotice(Connection conn, Notice notice) 
	{
		System.out.println("NoticeDao-deleteNotice");
		int result = 0;
		String sql = prop.getProperty("deleteNotice");
		//1.PreparedStatement 객체 생성 (sql) - 미완성 쿼리 값 대입
		try(
				PreparedStatement pstmt = conn.prepareStatement(sql);
				)
		{
			//2.쿼리 실행 pstmt.executeUpdate():int
			pstmt.setString(1, notice.getManager_id());//매니저 아이디
			pstmt.setInt(2, notice.getNotice_no());//글 번호
			pstmt.setString(3, notice.getNotice_pw());//글 비밀번호가 모두 일치 해야함.
			result = pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//쿼리 실행시 익셉션 발생시 throw
			throw new NoticeException("공지 삭제 오류",e);
		}
		//3.자원반납(pstmt) - 각 클래스에서 생성한 것만 반환 
		//try resource라 자동 할당해제
		return result;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	//1106 jin 관리자 아이디로 작성한 공지사항을 삭제하는 메소드

}
