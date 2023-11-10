package book.sheep.bookRental.model.vo;

import java.util.ArrayList;
import java.util.List;

import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.member.model.entity.Member;


// 11.09 멤버테이블 + 대여테이블 : JOIN 테이블
public class BookRentalMember extends Member {
    
	//원하는 컬럼값만 출력하기 위해  BookRentalPart라는 VO 테이블 만듬.
	private List<BookRentalPart> bookRentalparts = new ArrayList<>();

	public BookRentalMember() {
		super();
	}

	
	//member 테이블에서 원하는 컬럼만 따옴. (아이디,이름,전화번호)
	public BookRentalMember(String member_id, String member_phone, String member_name,
			 List<BookRentalPart> bookRentalparts) {
		super(member_id, member_phone, member_name);
	   this.bookRentalparts = bookRentalparts;
	}

	public List<BookRentalPart> getBookRentals() {
		return bookRentalparts;
	}

	public void setBookRentals(List<BookRentalPart> bookRentalparts) {
		this.bookRentalparts = bookRentalparts;
	}
	
	
    //책 대여정보 추가 메서드
	public void addBookRental (BookRentalPart bookRentalparts) {
		this.bookRentalparts.add(bookRentalparts);
	}
	
	
	
}//class 괄호
