package book.sheep.member.model.service;

import java.sql.Connection;
import java.util.List;

import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.getConnection;
import static book.sheep.common.JdbcTemplate.rollback;
import static book.sheep.common.JdbcTemplate.commit;

import book.sheep.member.model.dao.MemberDao;
import book.sheep.member.model.entity.Member;

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	public int insertMember(Member member) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.insertMember(conn, member);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}
		public Member findById(String id) {
			Connection conn = getConnection();
			Member member = memberDao.findById(conn, id);
			close(conn);
			return member;
	}

		public int updateMember(String id, String name, String newValue) {
			Connection conn = getConnection();
			int result = 0;
			try {
				result = memberDao.updateMember(conn, id, name, newValue);
				commit(conn);
			} catch (Exception e) {
				rollback(conn);
			} finally {
				close(conn);
			}
			return result;
		}
		
		//1107 입력한 회원 아이디와 비밀번호로 해당 회원이 있는지 조회함.
		public Member findByIdandPW(String id, String password) 
		{
			Connection conn = getConnection();
			Member member = memberDao.findByIdandPW(conn, id,password);
			close(conn);
			return member;
		}
		
		
		
		   //2. 배성은 11.08 모든 도서요청정보를 select
		public List<Member> findAllMember() {
			// 2.1 Connection 생성
			Connection conn = getConnection();
			// 2.2 Dao 호출s
			List<Member> members = memberDao.findAllMember(conn);
			// 2.3 자원반납
			close(conn);
			return  members ;
		}
		
		
		
		
}