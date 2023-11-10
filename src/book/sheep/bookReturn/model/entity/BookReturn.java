package book.sheep.bookReturn.model.entity;

import java.sql.Date;

import book.sheep.bookRental.model.entity.BookRental;


/**
 * @author 고혜진
 * 1109 
 * 테이블 이슈로 보류했던 tb_book_return 
 * 트리거로 data insert 확인 완료.
 * 실제로 사용자 아이디로 반납 완료된 내역 db 값을 읽어올 수 있도록 수정중.
 * 
 * 
 * 컬럼값
 *  return_no number not null,
   	member_id varchar2(30) not null,
   	book_no number not null,
   	book_title varchar2(200) not null,
   	rental_start date not null, 
   	rental_end date not null,
   	return_date date default sysdate not null,
 *
 */

public class BookReturn
{
	//컬럼값과 매칭되는 필드 7개
	private int return_no;
	private String member_id;
	private int book_no;
	private String book_title;
	private Date rental_start;
	private Date rental_end;
	private Date return_date; //실제 반납일
	
	public BookReturn(){}
	
	public BookReturn(int return_no,String member_id,int book_no,String book_title,
						Date rental_start, Date rental_end, Date return_date)
	{
		this.return_no = return_no;
		this.member_id = member_id;
		this.book_no = book_no;
		this.book_title = book_title;
		this.rental_start = rental_start;
		this.rental_end = rental_end;
		this.return_date = return_date;		
	}

	public int getReturn_no() {
		return return_no;
	}

	public void setReturn_no(int return_no) {
		this.return_no = return_no;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getBook_no() {
		return book_no;
	}

	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}

	public String getBook_title() {
		return book_title;
	}

	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}

	public Date getRental_start() {
		return rental_start;
	}

	public void setRental_start(Date rental_start) {
		this.rental_start = rental_start;
	}

	public Date getRental_end() {
		return rental_end;
	}

	public void setRental_end(Date rental_end) {
		this.rental_end = rental_end;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	@Override
	public String toString() {
		return "BookReturn [return_no=" + return_no + ", member_id=" + member_id + ", book_no=" + book_no
				+ ", book_title=" + book_title + ", rental_start=" + rental_start + ", rental_end=" + rental_end
				+ ", return_date=" + return_date + "]";
	}
	
	
}
