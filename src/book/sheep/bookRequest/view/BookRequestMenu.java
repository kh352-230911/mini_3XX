package book.sheep.bookRequest.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import book.sheep.bookRequest.controller.BookRequestController;
import book.sheep.bookRequest.model.entity.BookRequest;

public class BookRequestMenu {
	private Scanner sc = new Scanner(System.in);
//    private RequestMenu requestMenu = new RequestMenu();
    private BookRequestController bookRequestController =
    		new BookRequestController();
	  
    // [메인 콘솔]
    public void RequestMainMenu () {
      String menu = 
    """
   ===============================
	        책은 마음의 양식
   ===============================
		1. 도서 요청
		0. 이전 메뉴
   ===============================  
   선택  : """;	

      while(true) {
    	System.out.println();
    	System.out.println(menu);
    	String choice = sc.next();
    	
    	List<BookRequest> BookRequests = null; 
    	BookRequest bookRequest = null;
    	int result = 0;
   
    	//사용자가 입력한 값에 따른 분기점을 switch로 나눈다.
    	switch(choice) {
    	case "1" : 
    		bookRequest = inputbookReqeust();
    		result = bookRequestController.insertBookReqeust(bookRequest);
    		dispalayResult("도서요청", result);
    		break; //사용자가 요청할 도서의 정보를 입력해야함.   
    	case "0" : 
    		return;
    	default :
    		System.out.println("> 잘못입력하셨습니다 ");
    		break;
    	} //switch
    	
      }//while문
      
    }
    
  
	//1.사용자가 요청할 도서정보를 로직처리할 메서드.
    private BookRequest inputbookReqeust () {
    	BookRequest bookRequest = new BookRequest();
        System.out.println("==============도서요청 메뉴==============");       
          bookRequest =  new BookRequest ();   
    	 //원하는 도서의 이름입력,저자입력,출판사 입력  
          sc.nextLine();
          System.out.println("1.도서의 이름을 입력해주세요. :");
          bookRequest.setReqeustBookTitle(sc.nextLine());
          System.out.println("2.도서의 저자를 입력해주세요.  :");
          bookRequest.setReqeustBookAuthor(sc.nextLine());
          System.out.println("3.도서의 출판사를 입력해주세요.  :");
          bookRequest.setReqeustBookPublisher(sc.next());
          
          //도서요청 완료구문 출력
          System.out.println("[도서 요청이 성공적으로 완료 되었습니다!😀]");
          return bookRequest;
    
    }//1. inputbookReqeust
    
  
    
    //2. DML처리 결과 메소드.
    private void dispalayResult(String type, int result) {
		if(result > 0) 
		   System.out.println(type + "성공!");
		else
		   System.out.println(type + "실패 😥😥");
	} //2.dispalayResult


   //3. 관리자가 모든 도서요청 내용을 확인 할 수 있게 하는 메서드.
    public void displayBookRequest() {
    	List<BookRequest> brs = new ArrayList<>();
    	
    	brs = bookRequestController.findAllBookRequest();
    	
    	System.out.println("-------------------------------------------------------------------------------");
    	System.out.printf("%s \t %s \t %s \t %s \t %s \n",
    			          "no","회원 아이디","도서 이름","저자","출판사");
    	System.out.println("-------------------------------------------------------------------------------");
        if( brs.isEmpty() || brs == null) {
        	System.out.println("도서요청 사항의 조회된 결과가 없습니다. 😅");
            System.out.println("  ");
            System.out.println("-------------------------------------------------------------------------------");
        	System.out.println("0번을 누르면 이전메뉴로 돌아갑니다:");
        }    	
        else 
        {
        	//bookRequests LIST형태로, 테이블의 모든 값을 반환.
        	for(BookRequest bookRequest : brs) 
        	{
                System.out.printf("%d \t%s \t%s \t%s \t%s \n",
               		 bookRequest.getReqeustNo(),
               		 bookRequest.getMemberId(),
               		 bookRequest.getReqeustBookTitle(),
               		 bookRequest.getReqeustBookAuthor(),
               		 bookRequest.getReqeustBookPublisher());
           	}
        	System.out.println("-------------------------------------------------------------------------------");
        	System.out.println("삭제하고자 하는 도서요청 번호를 입력하세요(0번을 누르면 이전메뉴로 돌아갑니다) :");
        }
        
    } 
    
    private void displayResult(String type, int result) {
		if(result > 0)
			System.out.println("🎉🎉 " + type + " 성공! 🎉🎉");
		else
			System.out.println("😭😭 " + type + " 실패! 😭😭");				
	}
    
    
}//class 괄호
