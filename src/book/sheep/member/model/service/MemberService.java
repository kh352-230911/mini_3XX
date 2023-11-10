package book.sheep.member.model.service;

import java.sql.Connection;

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
		
}