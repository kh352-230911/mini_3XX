package book.sheep.bookRental.view;

import static book.sheep.common.LoginTemplate.ReadUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import book.sheep.book.model.entity.Book;
import book.sheep.book.view.BookMenu;
import book.sheep.bookRental.controller.BookRentalController;
import book.sheep.bookRental.model.vo.BookRentalMember;
import book.sheep.bookRental.model.vo.BookRentalPart;

/**
 * 1109 jin
 * 대여할 때 1.대여하기,0.뒤로가기 이렇게 사용자 입력값을 받기 전까지 대기하도록 했는데 
 * 회원이라면 그냥 대여하실 번호를 선택해주세요 뜨게 하는게 어떤가 싶음.
 * 
 * 1107 jin
 * 1.로그인한 회원 한정으로 도서 대여 가능함.
 * 2.대여가 불가능한 책은 선택할 수 없음.(book_is_rental = false)
 * 3.대여 정보는 TB_BOOK_RENTAL에 INSERT한다. 
 * 4.일단은 한회원이 한번당 한권의 책을 대여할 수 있다는 전제를 둠
 */
public class BookRentalMenu 
{
	private Scanner sc = new Scanner(System.in);
	BookRentalController bookRentalController = new BookRentalController();
	//1107 test sinsa 회원아이디로 책 대여하기..
	//insert into tb_book_rental values(seq_book_rental_no.nextval,'sinsa',6,'불편한 편의점2',default,default,0);
	List<Book> books = null;
	String searchType="";
	public String StartBookRentalMenu(List<Book> books,String searchType) 
	{
		this.searchType = searchType;
		//System.out.println("StartBookRentalMenu - checking");
		this.books= books;
		if(is_loginMember())
		{
			//System.out.println("일반회원-도서대여가 가능합니다.");
		}
		else
		{
			return "0"; //아니면 리턴시킴
		}
		
		int result=0;
		
		HashMap<String, Boolean> hm = ReadUser();
		String member_id=null;
		for (String key: hm.keySet()) 
		{
			member_id = key;
		}
		
		/**
		 * 1109 로그인한 회원이면 검색할 때 대여할 도서 선택하라고 띄움
		 * 사용자 입장 고려, 편하게 사용해야 하므로 책 고유번호는 알 필요 없다. 
		 * 조회된 결과값의 목록값(list index+1)만 출력함.
		 */
		System.out.println(">>대여하실 도서의 목록 번호를 입력해주세요.(돌아가기:0)");
		int userChoice = sc.nextInt(); //책번호 말고, 목록번호로 입력.
		
		if(userChoice==0) return "0";
		
		try
		{
			//System.out.println("선택한 도서 제목:"+books.get(userChoice-1).getBook_title());
			//올바른 목록번호를 입력했을 경우 해당 도서를 대여한다. and if대여가능한 책일때!
			if(books.get(userChoice-1).isBook_is_rental())
			{
				result = bookRentalController.insertBookRental(member_id,books.get(userChoice-1));
				displayResult("도서 대여",result);
				
			}
			else //isBook_is_rental() =false 대여중인상태
			{
				System.err.println("현재 대여중인 도서입니다. 다른 도서를 선택해주세요..");
				//기존에는 옳은 값을 입력해 대여가 될때까지 빠져나가지 못했는데 return추가해봄
				//return;
			}
		}
		catch(IndexOutOfBoundsException e) //목록번호 외의 값을 입력했을때 
		{
			System.err.println("잘못된 값을 입력하셨습니다. 다시 선택해주세요.");
		}
		
		
		
		
		
//		String menu ="""
//				+++++++++++++++++++++++++++++++++
//				1.대여 하기
//				0.이전 메뉴로
//				+++++++++++++++++++++++++++++++++
//				선택: """;
//		while(true)
//		{
//			System.out.print(menu);
//			String choice = sc.next();
//			//여기서 1.대여하기 0.이전으로 돌아가기 switch문만들것..[완]
//			switch(choice)
//			{
//			case "1":
//				System.out.println(">>대여할 도서의 목록 번호를 입력해주세요.");
//				int rentalBookNo = sc.nextInt(); //책번호 말고, 목록번호로 입력.
//				try
//				{
//					System.out.println("선택한 도서 제목:"+books.get(rentalBookNo-1).getBook_title());
//					//올바른 목록번호를 입력했을 경우 해당 도서를 대여한다. and if대여가능한 책일때!
//					if(books.get(rentalBookNo-1).isBook_is_rental())
//					{
//						result = bookRentalController.insertBookRental(member_id,books.get(rentalBookNo-1));
//						displayResult("도서 대여",result);
//						break;
//					}
//					else //isBook_is_rental() =false 대여중인상태
//					{
//						System.err.println("현재 대여중인 도서입니다. 다른 도서를 선택해주세요..");
//						//기존에는 옳은 값을 입력해 대여가 될때까지 빠져나가지 못했는데 return추가해봄
//						//return;
//					}
//				}
//				catch(IndexOutOfBoundsException e) //목록번호 외의 값을 입력했을때 
//				{
//					System.err.println("잘못된 값을 입력하셨습니다. 다시 선택해주세요.");
//				}
//				break;
//			case "0":
//				System.out.println("0번을 눌러 도서검색 메뉴로 되돌아가기.......");
//				return;
//				default :
//					System.err.println("잘못된 값을 입력하셨습니다.ㅠ");
//					break;
//			}//switch
//		}//while
		
		return searchType;
	}
	
	private boolean is_loginMember() 
	{
		boolean loginMember=false;
		HashMap<String, Boolean> hm =  ReadUser();
		boolean value = true;
		for (String key: hm.keySet()) 
		{
			value = hm.get(key);
			//System.out.println("Key1:" + key + ", Value1:" + value);	
		}
		//value가 false라면 일반회원->false->true
		return !value;
	}
	
	//1107 insert or delete 결과 출력 
	private void displayResult(String type, int result) 
	{
		if(result>0)
		{
			System.out.println(type+" 성공!💞");
		}
		else
		{
			System.out.println(type+" 실패!(ノ｀Д)ノ");
		}
	}
	
	//11.09 조인테이블 컬럼값을 출력하는 메서드	
    public void displayBookRentalMember() 
    {
    	List<BookRentalMember> brm = new ArrayList<>();
    	brm = bookRentalController.findAllBookRentalMember();
   	
    	System.out.println("-----------------------------------------------------------------------");
    	System.out.printf("%s \t%s \t%s \t%s \t%s      \t%s\n ",
   			          "회원이름","회원아이디","휴대폰번호","책 이름","대여한책고유번호"," 반납날짜");
    	System.out.println("-----------------------------------------------------------------------");
      	if(brm.isEmpty() || null == brm) 
      	{
      		System.out.println("조회된 회원정보가 없습니다...");
      		System.out.println("    ");
      	}
      	else 
      	{	
          BookRentalMember bookRentalmember = new BookRentalMember();
          for(BookRentalMember bookRentalmember2 : brm) 
          {
        	  //등록
       	   	  bookRentalmember =  bookRentalmember2;
       	   	  System.out.printf( "%s \t%s \t%s \n",
       			   bookRentalmember2.getMember_name(),
       			   bookRentalmember2.getMember_id(),
       			   bookRentalmember2.getMember_phone());
           }//for괄호
          
               
       	    List<BookRentalPart> brps = bookRentalmember.getBookRentals(); 
	            for(BookRentalPart brp : brps) { 
	        	    System.out.printf(" %s \t%d  \t%s \n",
	        	    	              brp.getBook_title(),
	        	    		          brp.getBook_no(),
	        	    		          brp.getRental_end());
	            }//for2
       }//else괄호
    
    }//displayBookRentalMember 괄호	
		

}


