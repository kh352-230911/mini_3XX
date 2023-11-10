package book.sheep.notice.view;

import static book.sheep.common.LoginTemplate.ReadUser;
import static book.sheep.common.LoginTemplate.RemoveUser;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import book.sheep.book.model.entity.Book;
import book.sheep.manager.model.entity.Manager;
import book.sheep.notice.controller.NoticeController;
import book.sheep.notice.model.entity.Notice;

/**
 * 1106 공지사항 관련 view단 - tb_notice
 * @author 고혜진
 * 
 * 1.관리자든, 일반회원이든 공지사항을 선택하면 기존 공지부터 모두 보여준다.
 * 2.관리자인 경우에만(hashmap check) 추가적으로 추가,삭제 선택 메뉴를 보여준다.(수정은 보류)
 */
public class NoticeMenu 
{
	List<Notice> notices = null;//notice객체를담을 list
	private Scanner sc = new Scanner(System.in);
	private NoticeController noticeController = new NoticeController();
	public void showNoticeMenu()
	{
		String noticeTitle = """
					[공지 사항]
							""";
	
		System.out.println(noticeTitle);
		notices = noticeController.findAllNotice();
		displayNotices(notices);
		//여기까지 공지사항 전체 보여주기 끝-------------------------------------------
		
		/*
		 * 1107 jin
		 * 아래부터 관리자인지 아닌지 확인하여 
		 * 0:이전으로 가기 외에 
		 * 1.공지 추가 2.공지 삭제 메뉴를 구현한다.. */
		HashMap<String, Boolean> hm = ReadUser();
		boolean is_manager=false;
		String manager_id=null;
		for (String key: hm.keySet()) 
		{
			is_manager = hm.get(key);
			manager_id = key;
		}
		
		//결과값 result
		int result = 0;
		//while
		if(is_manager)//매니저인경우
		{
			String noticeMenu = """
					=====================================
					1.공지 추가
					2.공지 삭제
					0.이전 메뉴로..
					=====================================
					선택:""";
			while(true)
			{
				System.out.println(noticeMenu);
				String choice = sc.next();
				switch (choice) 
				{
					case "1" : 
						//System.out.println("관리자 - 공지 추가");
						result = noticeController.insertNotice(inputNotice(manager_id));
						displayResult("공지 추가",result);
						//System.out.println("관리자 - 공지 추가 result:"+result);
					break;
					
					case "2":
						//System.out.println("관리자 - 공지 삭제");
						//1107 jin 공지 삭제
						result = noticeController.deleteNotice(deleteNotice(manager_id));
						displayResult("공지 삭제",result);
						break;
						
					case "0": 
						//System.out.println("관리자 - 이전 메뉴로..");
						return;
						
					default:
						//System.out.println("im manager :잘못 입력하셨습니다.");
						break;
				}//switch
			}//while
		}
		//일반회원,비회원인경우엔 추가 삭제 기능을 사용할 수 없으므로 공지확인 후 이전메뉴 이동만 가능
		else
		{
			String noticeMenu = """
					=====================================
					0.이전 메뉴로..
					=====================================
					선택:""";
			while(true)
			{
				System.out.println(noticeMenu);
				String choice = sc.next();
				switch (choice) 
				{
					case "0": 
						return;
					default:
						System.out.println("not manager : 잘못 입력하셨습니다.");
						break;
				}//switch
			}//while
		}
	}
	
	private Notice deleteNotice(String manager_id) 
	{
		//현재 관리자명을 매개변수로 받고. 공지사항 no와 pw를 사용자 입력받은 뒤 notice객체에 set한다.(총3개 set)
		Notice notice = new Notice();
		notice.setManager_id(manager_id);
		System.out.print(">>삭제하실 글 no를 입력하세요.(정수형)");
		notice.setNotice_no(sc.nextInt());
		System.out.print(">>글 비밀번호를 입력해주세요.");
		notice.setNotice_pw(sc.next());
		return notice;
	}

	//1107 insert or delete 결과 출력 
	private void displayResult(String type, int result) 
	{
		if(result>0)
		{
			System.out.println(type+" 성공!💞");
			notices = noticeController.findAllNotice();
			displayNotices(notices);
		}
		else
		{
			System.out.println(type+" 실패!(ノ｀Д)ノ");
		}
	}

	//공지사항 input
	private Notice inputNotice(String manager_id) 
	{
		Notice notice = new Notice();
		notice.setManager_id(manager_id);
		sc.nextLine();
		System.out.print(">>공지사항 내용을 입력해주세요. (공백포함)");
		String notce_content = sc.nextLine();
		notice.setNotice_content(notce_content);
		System.out.print(">>글 비밀번호를 입력해주세요.");
		notice.setNotice_pw(sc.next());
		return notice;
	}
	/**
	 * 1107 공지 삭제 
	 * 공지삭제 할 때 입력받아야 할 값.
	 * no, 해당 글의 pw (글마다 별개임) 둘 다 조건에 충족하지 않으면 삭제 할 수 없음.
	 * 
	 */
	





	//1106 jin 모든 공지사항 조회
	private void displayNotices(List<Notice> notices) 
	{
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.printf("%s \t%-40s \t%-15s \t%s\n", 
				"no", "공지 내용", "작성자", "작성날짜");
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		if(notices == null || notices.isEmpty()) {
			System.out.println("조회된 공지사항 결과가 없습니다.");
		}
		else 
		{
			for(Notice notice : notices) 
			{
				System.out.printf("%s \t%-40s \t%-15s \t%s\n", 
						notice.getNotice_no(),
						notice.getNotice_content(), 
						notice.getManager_id(), 
						notice.getNotice_date());
			
			}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		}
		
	}

}
