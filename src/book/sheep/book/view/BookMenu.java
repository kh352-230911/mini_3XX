package book.sheep.book.view;


import static book.sheep.common.LoginTemplate.ReadUser;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import book.sheep.book.controller.BookController;
import book.sheep.book.model.entity.Book;
import book.sheep.bookRental.view.BookRentalMenu;
import book.sheep.notice.model.entity.Notice;

/**
 * 1109
 * 대여 성공/실패 상관없이 매번 새로 select된 
 * 1106
 * @author 도서검색, 즉 책에 관련된 정보 출력 시 해당 클래스에서 보여주도록함.
 * 로그인한 일반회원만 대여가 보이게 해보자!
 * 1107
 * 도서정보에 출판사컬럼 추가.
 * 4.출판사로 검색 추가.
 */
public class BookMenu
{
	private BookRentalMenu bookRentalMenu = new BookRentalMenu();
	private Scanner sc = new Scanner(System.in);
	private BookController bookController = new BookController();
	List<Book> books = null;
	String userInputString=null;
	String choice = "";
	public void showBookMenu()
	{		
		String bookMenu = """
				=========== 도서 검색 ===========
				1. 도서 전체 검색
				2. 책 제목으로 검색
				3. 책 저자로 검색
				4. 출판사로 검색
				0. 이전으로 돌아가기
				===============================
				선택 : """;
		while(true) 
		{

			List<Book> books = null;
			System.out.println();
			System.out.println(bookMenu);
			choice = sc.next();
			switch(choice)
			{
			case "1":
				//전체 검색
				searchBook("1");
				break;
				
			case "2":
				//책 제목으로 검색
				searchBook("2");
				break;
				
			case "3":
				//책 저자로 검색
				searchBook("3");
				break;
				
			case "4":
				//출판사로 검색
				searchBook("4");
				break;
				
			case "0":
				//이전으로 돌아가기.
				return;
				
			default:
				//그외 입력
				System.out.println("잘못 입력하셨습니다.");
				break;
			}
		}
	}
	
	//1107 jin 입력받은 출판사 명으로 도서검색
	private String inputBookPublisher() 
	{
		System.out.print(">>출판사 명을 입력해주세요.");
		String bookPublisher = sc.next();
		userInputString = bookPublisher;
		return bookPublisher;
	}

	//1106 jin 입력받은 저자명으로 도서검색
	private String inputBookAuthor() 
	{
		System.out.print(">>찾으실 책 저자명을 입력해주세요.(공백없이)");
		String bookAuthor = sc.next();
		userInputString = bookAuthor;
		return bookAuthor;
	}
	
	//1106 jin 입력받은 책 제목으로 도서검색
	private String inputBookTitle() 
	{
		System.out.print(">>찾으실 책 제목을 입력해주세요.");
		sc.nextLine();
		String bookTitle = sc.nextLine();
		userInputString = bookTitle;
		return bookTitle;
	}

	/* 1109
	 * 전체검색
	 * 제목검색
	 * 저자검색
	 * 출판사로검색
	 * 사용자가 어떤것을 선택하냐에 따라 쿼리문이 달라지기때문에 결과값도 달라짐.
	 * 그에 맞는 List를 매개변수로 받아서 displayBooks 메소드를 통해 출력함. 
	 * */
	private void displayBooks(List<Book> books) 
	{
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.printf("%s \t%-30s \t%-15s \t%-15s \t%s\n", 
					"목록 번호", "책 제목", "저자", "출판사", "대여 가능 여부");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			if(books == null || books.isEmpty()) 
			{
				System.out.println("조회된 도서 결과가 없습니다......");
				
				//userInputString=null;
			}
			else 
			{
				for(int i=0; i<books.size(); i++) 
				{
					System.out.printf("%d \t%-30s \t%-15s \t%-15s  \t%s\n", 
							i+1,
							//books.get(i).getBook_no(),
							books.get(i).getBook_title(), 
							books.get(i).getBook_author(), 
							books.get(i).getBook_publisher(), 
							books.get(i).isBook_is_rental()? "가능":"불가능");
				}
			}
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}
		
	public void searchBook(String choice)
	{
		//System.out.println("searching..................");
		String backType=null; //==type
//		System.out.println("userInputString:"+userInputString);
//		System.out.println("choice:"+choice);
		if(choice==null)
			choice="";
		switch(choice)
		{
			case "1":
				//전체 검색
				books = bookController.findAllBook();
				displayBooks(books);
				if(books.size()>=1)
				backType = bookRentalMenu.StartBookRentalMenu(books,"1");
				break;
			
			case "2":
				//책 제목으로 검색
				if(userInputString==null)
					inputBookTitle();
				books = bookController.findBookByTitle(userInputString);
				displayBooks(books);
				if(books.size()>=1)
				backType = bookRentalMenu.StartBookRentalMenu(books,"2");
				break;
			
			case "3":
				//책 저자로 검색
				if(userInputString==null)
					inputBookAuthor();
				books = bookController.findBookByAuthor(userInputString);
				displayBooks(books);
				if(books.size()>=1)
				backType = bookRentalMenu.StartBookRentalMenu(books,"3");
				break;
			
			case "4":
				//출판사로 검색
				if(userInputString==null)
					inputBookPublisher();
				books = bookController.findBookByPublisher(userInputString);
				displayBooks(books);
				if(books.size()>=1)
				backType = bookRentalMenu.StartBookRentalMenu(books,"4");
				break;
				
				//사용자가 돌아가기 선택한 경우
//			case "0":
//				userInputString=null;
//				return;
				
				//그 외의 입력값1,2,3,4,0 외의
			default:
				//System.out.println("오류방지");
				userInputString=null;
				return;
					//break;
		}
		//System.out.println("...........backType:"+backType);
		searchBook(backType);
	}

	//1107 jin 관리자인 경우(manager_id) 도서 추가/삭제 권한을 수행할 수 있다.
	public void manageBookMenu() 
	{
		//전체 도서 출력
		books = bookController.findAllBook();
		displayBooks(books);

		int result = 0;
		//System.out.println("BookMenu - manageBookMenu() ");	
		String menu = """
				=====================================
				1.도서 추가
				2.도서 삭제
				0.이전 메뉴로..
				=====================================
				선택:""";
		while(true)
		{
			System.out.println(menu);
			String choice = sc.next();
			switch (choice) 
			{
				case "1" : 
					//System.out.println("im manager - 도서 추가");
					/**
					 * 1107 jin 도서 추가
					 * 필요한 정보 책이름/저자/장르[숫자]/대여여부기본값default/
					**/
					result = bookController.insertBook(inputBook());
					displayResult("도서 추가",result);
					//System.out.println("관리자 - 도서 추가 result:"+result);
				break;
				
				case "2":
					//System.out.println("im manager - 도서 삭제");
					//1107 jin 도서 삭제 - 조건) 현재 대여상태가 true인 것만.(false:대여중)
					result = bookController.deleteBook(deleteBook());
					displayResult("도서 삭제",result);
					break;
					
				case "0": 
					return;
					
				default:
					//System.out.println("im manager :잘못 입력하셨습니다.");
					break;
			}//switch
		}//while
	}

	
	//새로운 도서 추가
	private Book inputBook() 
	{
		//System.out.println("추가할 도서 정보 입력하는 메소드 inputBook()");
		//필요한 정보 책이름/저자/출판사명/장르[숫자]/대여여부기본값default/
		Book book = new Book();
		
		sc.nextLine();
		System.out.print(">>책 제목을 입력해주세요.");
		book.setBook_title(sc.nextLine());
		System.out.print(">>책 저자명을 입력해주세요.");
		book.setBook_author(sc.nextLine());
		System.out.print(">>책 출판사명을 입력해주세요.");
		book.setBook_publisher(sc.nextLine());
		book.setBook_type(1);
		return book;
	}
	
	
	//기존 도서 삭제시 , book_is_rental값에 따라 삭제 여부가 결정됨.
	//일단 book_no를 입력받는다..
	private Book deleteBook() 
	{
		Book book = new Book();
		System.out.print(">>삭제하실 도서 목록 번호를 입력하세요.");
		book.setBook_no(sc.nextInt());
		return book;
	}


	//1107 jin 도서 추가/삭제 결과를 알려주는 메소드
	private void displayResult(String type, int result) 
	{
		if(result>0)
		{
			System.out.println(type+"에 성공하였습니다.");
			//성공하면 전체 도서를 보여준다..
			books = bookController.findAllBook();
			displayBooks(books);
		}
		else
		{
			System.out.println(type+"에 실패 하였습니다. ");
		}
		
	}
	
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
