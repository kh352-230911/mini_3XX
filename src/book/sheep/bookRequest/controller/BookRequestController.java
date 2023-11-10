package book.sheep.bookRequest.controller;

import java.util.List;

import book.sheep.bookRequest.model.entity.BookRequest;
import book.sheep.bookRequest.model.service.BookRequestService;

//컨드롤러의 클래스는, 메서드로 처리된 값을 view로 return해야함.
public class BookRequestController {
     private BookRequestService bookRequestService 
     = new BookRequestService();
	
	//1. book_reqeust 테이블에 row insert하는 메서드.
	  //result가 0 이상이면 정상작동
	public int insertBookReqeust(BookRequest bookRequest) {
      	int result = 0;
        try {
           result = bookRequestService.insertBookRequest(bookRequest);	
        } catch (Exception e) {
        	e.printStackTrace();
        	//오류 메세지 전송
        	System.out.println("불편을 드려 죄송합니다. : " + e.getMessage());
        }
		return result;	
	}
	
	//2. 컬럼내용을 모두 조회하는 메서드
	public List<BookRequest> findAllBookRequest () {
		List<BookRequest> bookRequests = null;
	try {	
	bookRequests =  bookRequestService.findAllBookRequest();
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("> 불편을 드려 죄송합니다. : "+ e.getMessage());
	}
	
	  return bookRequests;
	}
	
	
	public int deleteBookRequest(int requestNo) {
		int result = 0;
		try {
			result = bookRequestService.deleteBookRequest(requestNo);
			System.out.println(requestNo +"번 도서요청이 삭제 되었습니다. 😀😀");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	
	}
	
} //class 괄호
