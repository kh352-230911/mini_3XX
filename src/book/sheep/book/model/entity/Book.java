package book.sheep.book.model.entity;

/**
 * 2023-11-05
 * @author 고혜진
 * book info 값을 담는 book객체 클래스
 *  
 */
public class Book 
{
	private int book_no; //책 넘버(고유번호,pk,시퀀스로 생성)
	private String book_title; //책 이름
	private String book_author;//책 저자
	private String book_publisher; //책 출판사
	private int book_type; //책 타입(장르별)
	private int same_book_count; //동일권수(사용유무 확실치 않지만 일단 컬럼값이 있으므로 필드생성함)
	private boolean book_is_rental; //대여 가능유무 1:true/0:false
	
	//기본생성자
	public Book()
	{
	}
	
	//매개변수 생성자
	public Book(int book_no, String book_title, String book_author, String book_publisher,
			int book_type, int same_book_count, boolean book_is_rental)
	{
		this.book_no = book_no;
		this.book_title = book_title;
		this.book_author = book_author;
		this.book_publisher = book_publisher;
		this.book_type = book_type;
		this.same_book_count = same_book_count;
		this.book_is_rental = book_is_rental;	
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

	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public int getBook_type() {
		return book_type;
	}

	public void setBook_type(int book_type) {
		this.book_type = book_type;
	}

	public int getSame_book_count() {
		return same_book_count;
	}

	public void setSame_book_count(int same_book_count) {
		this.same_book_count = same_book_count;
	}

	public boolean isBook_is_rental() {
		return book_is_rental;
	}

	public void setBook_is_rental(boolean book_is_rental) {
		this.book_is_rental = book_is_rental;
	}

	public String getBook_publisher() {
		return book_publisher;
	}

	public void setBook_publisher(String book_publisher) {
		this.book_publisher = book_publisher;
	}

	@Override
	public String toString() {
		return "Book [book_no=" + book_no + ", book_title=" + book_title + ", book_author=" + book_author
				+ ", book_publisher=" + book_publisher + ", book_type=" + book_type + ", same_book_count="
				+ same_book_count + ", book_is_rental=" + book_is_rental + "]";
	}

	
	
	
	
	
}
