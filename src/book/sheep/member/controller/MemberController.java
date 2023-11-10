package book.sheep.member.controller;

import java.util.List;

import book.sheep.member.model.entity.Member;
import book.sheep.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}

	public int updateMember(String id, String name, String newValue) {
		int result = 0;
		try {
			result = memberService.updateMember(id, name, newValue);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("정보 수정에 실패하였습니다." + e.getMessage());
		}
		return result;
	}

	public Member findById(String id) {
		Member member = null;
		try {
			member = memberService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return member;
	}

	//1107 입력한 회원의 아이디와 비밀번호를 가지고 tb_member에 해당 멤버가 있는지 select로 검사
	public Member findByIdandPW(String id, String pw) 
	{
		Member member = null;
		try {
			member = memberService.findByIdandPW(id,pw);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return member;
	}
	
	   //1108 배성은 회원 이름/아이디/휴대폰 번호 찾는 메서드
		public List<Member> findAllMember() {
			List<Member> members = null;
			try {	
			  members =  memberService.findAllMember();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("> 불편을 드려 죄송합니다. : "+ e.getMessage());
			}
			
			  return members;
		} 
	
	
}