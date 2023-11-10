package book.sheep.member.view;

import static book.sheep.common.LoginTemplate.ReadUser;
import static book.sheep.common.LoginTemplate.RemoveUser;
import static book.sheep.common.LoginTemplate.saveMember;

import java.util.Scanner;

import book.sheep.book.view.BookMenu;
import book.sheep.bookRental.view.BookRentalMenu;
import book.sheep.bookRequest.view.BookRequestMenu;
import book.sheep.bookReturn.view.BookReturnMenu;
import book.sheep.manager.view.ManagerMenu;
import book.sheep.member.controller.MemberController;
import book.sheep.member.model.entity.Member;
import book.sheep.notice.view.NoticeMenu;

/*
 * 
 * 1109 test용 6. 반납 내역 추가.
 * 
 * 
 * 기존 메뉴
===============================
       책은 마음의 양식
===============================
1. 도서 대여
2. 도서 반납
3. 도서 요청
4. 리뷰 게시판
5. 내 정보 수정
0. 로그아웃
===============================
*/

public class MemberMenu {
	
	//도서요청
	private BookRequestMenu requestMenu = new BookRequestMenu();
	//1108 공지 사항 (read only),도서반납 return
	private NoticeMenu noticeMenu = new NoticeMenu();
	private BookReturnMenu bookReturnMenu = new BookReturnMenu();
	//1107 도서 대여
	private BookRentalMenu bookRentalMenu = new BookRentalMenu();
	private BookMenu bookMenu = new BookMenu();
	private MemberController memberController = new MemberController();
	private  Scanner sc = new Scanner(System.in);
	
	public void mainMenu(Member m) 
	{
		saveMember(m);
		String menu = """
				===============================
					  책은 마음의 양식
				===============================
				1. 도서 대여
				2. 도서 반납
				3. 도서 요청
				4. 공지사항
				5. 내 정보 수정
				6. 도서 반납 내역
				0. 로그아웃
				===============================	
				선택 : """;
		
		while(true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			
			int result = 0;
			Member member = null;
			String Book = null;			
			
			switch (choice) {
			case "1" : 
//				book_rental();
				//System.out.println("회원 - 도서 대여");
				bookMenu.showBookMenu();
				//bookRentalMenu.showBookRentalMenu();
				break;
			
			case "2":
				/*
				 *1108 도서 반납선택하면 현재 내 아이디로 대여한 결과 보여주기.
				 * select * from tb_book_rental where member_id='jin';
				 */
				bookReturnMenu.showBookReturnMenu();
				//System.out.println("회원 - 도서 반납");
				break;
			
			case "3": 
				requestMenu.RequestMainMenu();
				//System.out.println("회원 - 도서 요청");
				break;
				
			case "4": 
				//1108 로그인 한 후 공지사항 보기.
				noticeMenu.showNoticeMenu();
				//System.out.println("공지사항");
				break;
				
			case "5":
				updateMemberMenu();
				//System.out.println("내 정보 수정");
				break;
				
			//1109
			case "6":
				bookReturnMenu.showReturnList();
				//System.out.println("반납 내역");
				break;
				
			case "0": 
				//System.out.println("로그아웃 하셨습니다.");
				RemoveUser();
				return;
				
			default:
				//System.out.println("잘못 입력하셨습니다.😥");
				ReadUser();
				break;
			}
		}
	}


	private void updateMemberMenu() {
		String menu = "========== 회원 정보수정 ==========\n"
				+ "1. 비밀번호 수정\n"
				+ "2. 전화번호 수정\n"
				+ "3. 이름 수정\n"
				+ "0. 이전 메뉴로 돌아가기\n"
				+ "===============================\n"
				+ "선택 : ";
		
		String id = inputId();
		
		while(true) 
		{
			Member member = memberController.findById(id);
			displayMember(member);
			if(member == null) {
				return;
			}
			
			System.out.print(menu);
			String choice = sc.next();
			String Name = null;
			String newValue = null;
			
			switch(choice) {
			case "1" : 
				System.out.print("변경할 비밀번호 : ");
				Name = "member_pw";
				newValue = sc.next();
				break;
				
			case "2" : 
				System.out.print("변경할 전화번호 : ");
				Name = "member_phone";
				newValue = sc.next();
				break;
				
			case "3" : 
				System.out.print("변경할 이름 : ");
				Name = "member_name";
				newValue = sc.next();
				break;
				
			case "0" : return;
			
			default : 
				System.out.println("잘못 입력하셨습니다.😥");
				continue;
			}
			
			int result = memberController.updateMember(id, Name, newValue);
			displayResult("회원 정보 수정", result);
		}
	}


	private void displayMember(Member member) {
		if(member == null) {
			System.out.println("조회에 실패 하셨습니다.😥");
		}
		else {
			System.out.println("-----------------------------------------");
			System.out.printf("Password : %s\n", member.getMember_pw());
			System.out.printf("Phone 	 : %s\n", member.getMember_phone());
			System.out.printf("Name 	 : %s\n", member.getMember_name());
			System.out.println("-----------------------------------------");
		}
	}

	private void displayResult(String str, int result) {
		if(result > 0)
			System.out.println("🎉🎉 " + str + " 성공! 🎉🎉");
		else
			System.out.println("😭😭 " + str + " 실패! 😭😭");				
	}

	private String inputId() {
		System.out.print("아이디 : ");
		return sc.next();
	}
	
	
}