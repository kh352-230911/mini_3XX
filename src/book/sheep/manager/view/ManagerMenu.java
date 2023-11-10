package book.sheep.manager.view;

import static book.sheep.common.LoginTemplate.RemoveUser;
import static book.sheep.common.LoginTemplate.saveManager;

import java.util.Scanner;

import book.sheep.book.view.BookMenu;
import book.sheep.bookRental.view.BookRentalMenu;
import book.sheep.bookRequest.controller.BookRequestController;
import book.sheep.bookRequest.view.BookRequestMenu;
import book.sheep.manager.model.entity.Manager;
import book.sheep.member.view.MemberMenu;
import book.sheep.notice.view.NoticeMenu;

/**
 * 2023-11-05
 * @author 고혜진
 * 로그인 성공시 매니저 메뉴 콘솔 출력.
 * 
 * 취침 전에 메모
 * 아이디 패스워드 입력을 통해 해당 테이블에 조회된 회원의 정보를 저장해야할 것 같다.
 * 지금까지는 단순히 아이디나 특정 값으로 조회까지만 했는데, 리뷰를쓴다거나 도서대여를 할때엔 로그인이 필요하므로 아이디 값을 저장할 필요가 있음.
 */
public class ManagerMenu 
{
	private BookRequestController bookRequestController = new BookRequestController();
	private BookRentalMenu bookRentalMenu = new BookRentalMenu();
	private MemberMenu memberMenu = new MemberMenu();
	private BookRequestMenu requestMainMenu = new BookRequestMenu();//도서요청관리
	//회원관리
	private BookMenu bookMenu = new BookMenu();//도서관리
	private NoticeMenu noticeMenu = new NoticeMenu();//공지관리
	//private NoticeMenu noticeMenu = new NoticeMenu();//도서요청관리
	private Scanner sc = new Scanner(System.in);
	public void showManagerMenu(Manager m) 
	{
		
		saveManager(m); //현재 로그인한 manager 객체의 값을 저장한다.
		String ManagerMenu = """
				===============================
					책은 마음의 양식(관리자)
				===============================
				1. 회원 관리 
				2. 도서 관리 
				3. 공지 관리 
				4. 도서 요청 관리
				0. 로그아웃
				===============================
				선택 : """;
		
		while(true)
		{
			System.out.println(ManagerMenu);
			String choice = sc.next();
			String id=null;
			String password=null;
			Manager manager=null;
			
			switch (choice) 
			{
			case "1" : 
				
				//System.out.println("관리자 - 회원 관리");
//				bookRentalMenu.displayBookRentalMember();
				memberMenu.displayMemberRequest();
				break;
			case "2":
				//System.out.println("관리자 - 도서 관리");
				bookMenu.manageBookMenu();
				break;
			case "3": 
				//System.out.println("관리자 - 공지 관리 추가,수정,삭제");
				noticeMenu.showNoticeMenu();
				break;
//			case "4" : 
//				System.out.println("관리자 - 도서 요청 관리");
//				requestMainMenu.displayBookRequest();
//				//사용자가 요청한 도서정보가 모두 출력되게 만들어야한다.완료! (배성은)
//				break;
				
				//
			case "4" : 
                //System.out.println("관리자 - 도서 요청 관리");
                requestMainMenu.displayBookRequest();
       		 	int i = sc.nextInt();
                if(i == 0) 
                {	
               		break;
                }
                else
                {
                	bookRequestController.deleteBookRequest(i);
                	requestMainMenu.displayBookRequest();
         			System.out.println(i +"번 도서요청이 삭제 되었습니다. 😀😀");
                }
         
                break;
				
				
			case "0": 
				//System.out.println("로그아웃 합니다- 이전 메뉴로 돌아감.");
				//로그아웃 할때 hashmap에 저장된 값을 비운다.
				RemoveUser();
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;
			}
		}//while	
		
	}

}