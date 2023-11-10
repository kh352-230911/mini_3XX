package book.sheep.main;


import static book.sheep.common.LoginTemplate.ReadUser;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


import book.sheep.book.controller.BookController;
import book.sheep.book.model.entity.Book;
import book.sheep.book.view.BookMenu;
import book.sheep.manager.controller.ManagerController;
import book.sheep.manager.model.entity.Manager;
import book.sheep.manager.view.ManagerMenu;
import book.sheep.member.controller.MemberController;
import book.sheep.member.model.entity.Member;
import book.sheep.member.view.MemberMenu;
import book.sheep.notice.view.NoticeMenu;


/**
 * 2023-11-05
 * @author 고혜진
 * 
 *	===============================
        	책은 마음의 양식
	===============================
	1. 로그인
	2. 회원가입
	3. 공지사항 
	4. 도서 검색 
	0. 종료
	===============================
 */

public class MainMenu 
{
	private BookController bookController = new BookController();
	private ManagerController managerController = new ManagerController();
	private Scanner sc = new Scanner(System.in);
	private ManagerMenu managerMenu = new ManagerMenu();
	private NoticeMenu noticeMenu = new NoticeMenu();
	private BookMenu bookMenu = new BookMenu();
	
	//1107 멤버 패키지 추가
	private MemberMenu memberMenu = new MemberMenu();
	private MemberController memberController = new MemberController();
	
	public void showMainMenu() 
	{
		String menu = """
				===============================
					책은 마음의 양식
				===============================
				1. 로그인
				2. 회원가입
				3. 공지사항 
				4. 도서 검색 
				5. 관리자 로그인
				0. 종료
				===============================	
				선택 : """;

		while(true) 
		{
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			Manager manager=null;//관리자
			Member member = null; //일반회원
			String id=null;
			String password=null;
			int result = 0;
			switch (choice) 
			{
			case "1" : // 일반회원 로그인
				//System.out.println("일반회원 로그인");
				System.out.println("===============================	");
				System.out.println("아이디를 입력하세요. : ");
				String memberId= sc.next();
				System.out.println("비밀번호를 입력하세요. : ");
				String memberPw = sc.next();
				System.out.println("===============================	");
				member = memberController.findByIdandPW(memberId,memberPw);
				displayMember(member);
				//memberMenu.mainMenu();
			
				break;
			case "2": // 회원가입
				member = inputMember();
				result = memberController.insertMember(member);
				displayMember("회원가입", result);
				break;
				
			case "3": 
				//System.out.println("공지사항");
				noticeMenu.showNoticeMenu();
				break;
				
			case "4": 
				System.out.println("도서 검색");
				bookMenu.showBookMenu();
				break;
				
			case "5" : 
				//로그인을 하면 member - view단으로 이동
				System.out.println("관리자 로그인");
				String[] loginManagerArray = login_manager();
				manager = managerController.managerLogin(loginManagerArray[0],loginManagerArray[1]);
				displayMember(manager);
				break;
				
			case "0": return;
			
			default:
				System.out.println("잘못 입력하셨습니다.");
				ReadUser();
				break;
			}
		}//while
	}
	
	
	private static void displayMember(String str, int result) {
		if(result > 0) 
			System.out.println("🎉🎉 " + str + " 성공! 🎉🎉");
		else
			System.out.println("😭😭 " + str + " 실패! 😭😭");				
	}
	
	/**
	 * 1건의 회원정보 조회
	 * @param member
	 */
	private void displayMember(Object o) 
	{
		if(o == null) 
		{
			System.out.println("> 조회된 사용자가 없습니다.아이디나 비밀번호를 확인해주세요.");
		}
		else 
		{
			//1건이상 조회된 성공결과가 있다는 뜻
			if (o instanceof Manager) 
			{
				//System.out.println("로그인 결과, 당신은 관리자입니다.");
				Manager manager = (Manager) o;
				//System.out.println(manager.toString());
				managerMenu.showManagerMenu(manager);
			}
			else if(o instanceof Member)
			{
				//System.out.println("로그인 결과, 당신은 일반회원입니다.");
				Member member = (Member) o;
				//System.out.println(member.toString());
				memberMenu.mainMenu(member);
			}
		
		}
	}
	
	//메인메뉴-관리자 로그인단 3XX XXX or testManager 1234
	private String[] login_manager() 
	{
		String arr[] = new String[2];
		String menu = """
				===============================
					관리자 로그인
				===============================
				""";
		System.out.println(menu);
		System.out.println(">ID : ");
		arr[0] = sc.next();
		System.out.println(">PASSWORD : ");
		arr[1] = sc.next();
		return arr;
	}
	
	private Member inputMember() {
		Member member = new Member();
		member.setMember_id(checkIdDuplicate()); // 중복아이디체크	
		System.out.print("비밀번호를 입력하세요. : ");
		member.setMember_pw(sc.next());
		System.out.print("전화번호를 입력하세요. : ");
		member.setMember_phone(sc.next());
		System.out.print("이름을 입력하세요. : ");
		member.setMember_name(sc.next());
	return member;
	}
	
	private String checkIdDuplicate() {
		String id = null;
        do {
            if(id != null)
                System.out.printf("[%s]는 이미 사용중입니다ㅠㅠ%n", id);
            System.out.print("아이디를 입력하세요. : ");
            id = sc.next();
        } while(memberController.findById(id) != null);
        return id;
    }
}
