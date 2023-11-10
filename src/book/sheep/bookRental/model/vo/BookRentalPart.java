package book.sheep.bookRental.model.vo;

import java.sql.Date;

public class BookRentalPart {
	private int book_no;
	private String book_title;
	private Date rental_end;
	
	public BookRentalPart() {
		super();
	}

	public BookRentalPart(int book_no, String book_title, Date rental_end) {
		super();
		this.book_no = book_no;
		this.book_title = book_title;
		this.rental_end = rental_end;
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

	public Date getRental_end() {
		return rental_end;
	}

	public void setRental_end(Date rental_end) {
		this.rental_end = rental_end;
	}

	@Override
	public String toString() {
		return "BookRentalPart [book_no=" + book_no + ", book_title=" + book_title + ", rental_end=" + rental_end + "]";
	}

        


}
