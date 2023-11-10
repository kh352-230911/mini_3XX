package book.sheep.bookRental.controller;

import java.util.List;

import book.sheep.book.model.entity.Book;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookRental.model.service.BookRentalService;
import book.sheep.bookRental.model.vo.BookRentalMember;

public class BookRentalController {

	private BookRentalService bookRentalService = new BookRentalService();

	public int insertBookRental(String member_id,Book book) 
	{
		int result = 0;
		try {
			result = bookRentalService.insertBookRental(member_id,book);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 대여책에서 삭제[반납]
	 * 
	 */
	public int deleteBookRental(BookRental bookRental) 
	{
		//System.out.println("빌린 책 삭제controller");
		int result = 0;
		try {
			result = bookRentalService.deleteBookRental(bookRental);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 1108
	 * findAllMyBookRental
	 * 회원의 대여 내역을 조회하는 메소드 
	 */
	public List<BookRental> findAllMyBookRental(String id) 
	{
		List<BookRental> bookRentals = null;
		try 
		{
			bookRentals = bookRentalService.findAllMyBookRental(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return bookRentals;
	}
	
	/**
	 * 1109
	 */

	//2. 원하는 컬럼내용 모두 조회하는 메서드
	public List<BookRentalMember> findAllBookRentalMember () {
		List<BookRentalMember> bookRentalMembers = null;
		try {
		bookRentalMembers = bookRentalService.findAllBookRentalMember();
		} catch (Exception e) {
			e.printStackTrace();
		     System.out.println("> 불편을 드려 죄송합니다 :" + e.getMessage());
		}
		return bookRentalMembers;
	}
}
