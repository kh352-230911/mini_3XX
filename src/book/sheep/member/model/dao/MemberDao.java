package book.sheep.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import book.sheep.bookRental.model.vo.BookRentalMember;
import book.sheep.bookRequest.model.exception.BookRequestException;
import book.sheep.manager.model.entity.Manager;
import book.sheep.manager.model.exception.ManagerException;
import book.sheep.member.model.entity.Member;
import book.sheep.member.model.exception.MemberException;

public class MemberDao {

	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.load(new FileReader("resources/member-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setString(1, member.getMember_id());	
		pstmt.setString(2, member.getMember_pw());	
		pstmt.setString(3, member.getMember_name());
		pstmt.setString(4, member.getMember_phone());	
		result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			throw new MemberException("회원가입 오류", e);
		}
		return result;
	}

	public Member findById(Connection conn, String id) {
		Member member = null;
		String sql = prop.getProperty("findById");
	
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery();){
				if(rset.next())
					member = handleMemberResultSet(rset);
			}
			
		} catch (SQLException e) {
			throw new MemberException("회원 아이디 조회 오류!", e);
		} 
		return member;
	}

	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setMember_id(rset.getString("member_id"));
		member.setMember_pw(rset.getString("member_pw"));
		member.setMember_name(rset.getString("member_name"));
		member.setMember_phone(rset.getString("member_phone"));

		return member;
	}

	public int updateMember(Connection conn, String id, String column, String newValue) {
		String sql = prop.getProperty("updateMember");
		sql = sql.replace("#", column);
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, newValue);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new MemberException("정보 수정 오류" , e);
		}
		return result;
	}

	//1107 입력한 회원 아이디와 비밀번호로 회원의 존재 확인
	public Member findByIdandPW(Connection conn, String id, String password) 
	{
		String sql = prop.getProperty("findByIdandPW");
		Member member = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					member = new Member();
					// 회원관련
					member.setMember_id(rset.getString("member_id"));
					member.setMember_pw(rset.getString("member_pw"));
					member.setIs_manager(rset.getInt("is_manager")==1);//일반회원:false
				}
			}
		} catch (SQLException e) {
			throw new ManagerException("회원 로그인 오류!(아이디,비밀번호 매칭확인 )", e);
		}
	
		return member;
	}
	
	//11.08 배성은 - 회원의 이름/아이디/휴대폰번호정보를 불러오는 쿼리를 읽어서 그 결과값을 LIST에 담는 메서드 
		public List<Member> findAllMember(Connection conn) {
			List<Member>  memberList = new ArrayList<>();
			
			//2.1포로퍼티에서 작성한 쿼리문중 읽어올 쿼리문 이름 등록
			String sql = prop.getProperty("findmember");
			
			//2.2 PreparedStatement 생성.
			try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				//2.3쿼리 실행 : pstmt.executeQuery() 해서, 결과값을 Result set에 담는다.
				
				   //이 쿼리문을 수정해서, member랑 rental이랑 join한 결과를 가져오게 만들어보면 어떨가? (11/09)
			    try(ResultSet rset = pstmt.executeQuery()) {
			      //2.4 result set의 결과를 쪼개서, 자바의 필드값에 컬럼값을 기입.
			      while (rset.next()) {
			    	  Member member = handleMemberResultSet2(rset);
			    	  memberList.add(member); 
			      }//while	
			    } //try2	
				
			} catch (SQLException e) {
			   throw new BookRequestException("회원정보 조회 오류!", e);
			}
			//2.5 member 객체를 LIST에 담아 반환. 어디로? service class로
			return memberList ;
		}
		
		
		//11.08 DB에서 회원의 이름/아이디/휴대폰 번호만 가져오는 메서드 (배성은)
		private BookRentalMember handleMemberResultSet2(ResultSet rset) throws SQLException {
			BookRentalMember memberBookRental = new BookRentalMember();
			memberBookRental.setMember_name(rset.getString("member_name"));
			memberBookRental.setMember_id(rset.getString("member_id"));
			memberBookRental.setMember_phone(rset.getString("member_phone"));
			
			return memberBookRental;
		}
}