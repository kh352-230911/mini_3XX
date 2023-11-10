package book.sheep.bookRental.model.entity;

import java.sql.Date;

//1109 book_no String->int수정
//1107 jin tb_book_rental과 매칭되는 객체클래스

public class BookRental 
{
	private int rental_no;
	private String member_id;
	private int book_no;
	private String book_title;
	private Date rental_start;
	private Date rental_end;
	private boolean book_is_rental;
	//1108 컬럼하나 더 추가
	private boolean rental_state;
	
	//기본생성자
	public BookRental() {}
	
	//매개변수 생성자..
	public BookRental(int rental_no,String member_id,int book_no,String book_title,
					Date rental_start,Date rental_end, boolean book_is_rental,boolean rental_state)
	{
		this.rental_no = rental_no;
		this.member_id = member_id;
		this.book_no = book_no;
		this.book_title = book_title;
		this.rental_start = rental_start;
		this.rental_end = rental_end;
		this.book_is_rental = book_is_rental;
		this.rental_state = rental_state;
	}

	public int getRental_no() {
		return rental_no;
	}

	public void setRental_no(int rental_no) {
		this.rental_no = rental_no;
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

	public boolean isBook_is_rental() {
		return book_is_rental;
	}

	public void setBook_is_rental(boolean book_is_rental) {
		this.book_is_rental = book_is_rental;
	}

	//1108 대여상태 추가 대여중../대여 종료 이런식
	public boolean isRental_state() {
		return rental_state;
	}

	public void setRental_state(boolean rental_state) {
		this.rental_state = rental_state;
	}

	@Override
	public String toString() {
		return "BookRental [rental_no=" + rental_no + ", member_id=" + member_id + ", book_no=" + book_no
				+ ", book_title=" + book_title + ", rental_start=" + rental_start + ", rental_end=" + rental_end
				+ ", book_is_rental=" + book_is_rental + ", rental_state=" + rental_state + "]";
	}
	
	
	
	
}
