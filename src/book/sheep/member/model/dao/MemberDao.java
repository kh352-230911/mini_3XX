package book.sheep.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
		pstmt.setString(3, member.getMember_phone());	
		pstmt.setString(4, member.getMember_name());
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
		member.setMember_phone(rset.getString("member_phone"));
		member.setMember_name(rset.getString("member_name"));

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
}
