package book.sheep.bookRequest.model.vo;

import java.util.ArrayList;
import java.util.List;

import book.sheep.bookRequest.model.entity.BookRequest;
import book.sheep.member.model.entity.Member;

//vo클래스,
  //실제테이블이 존재하는건 아닌데, join등 홗장된 result set을 처리하기 위한 클래스
public class MemberBookRequest extends Member {
	
    private List<BookRequest> bookRequests = new ArrayList<>();

	public MemberBookRequest() {
		super();
	}
     
	public MemberBookRequest(String member_id, String member_pw, String member_phone, String member_name,
			boolean is_manager, List<BookRequest> bookRequests ) {
		super(member_id, member_pw, member_phone, member_name, is_manager);
		this.bookRequests = bookRequests;
	}
	
	//getter, setter
	public List<BookRequest> getBookRequests() {
		return bookRequests;
	}

	public void setBookRequests(List<BookRequest> bookRequests) {
		this.bookRequests = bookRequests;
	}

	//도서요청 추가 편의 메서드
	public void addbookRequest(BookRequest bookRequest) {
		this.bookRequests.add(bookRequest);
	}
	
	
	@Override
	public String toString() {
		return "MemberBookRequest [bookRequests=" + super.toString() + bookRequests + "]";
	} 

	
   
    
    
}//class 괄호
