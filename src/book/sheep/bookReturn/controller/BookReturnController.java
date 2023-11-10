package book.sheep.bookReturn.controller;

import java.util.List;

import book.sheep.book.model.entity.Book;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookReturn.model.entity.BookReturn;
import book.sheep.bookReturn.model.service.BookReturnService;

public class BookReturnController 
{
	/**
	 * 1108 책 반납 
	 */
	private BookReturnService bookReturnService = new BookReturnService();

	public int insertBookReturn(BookRental bookRental) 
	{
		int result = 0;
		try 
		{
			result = bookReturnService.insertBookReturn(bookRental);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * 1109 책 반납의 모든 내역 확인하기. (현재 사용자 아이디로 조회)
	 */
	
	public List<BookReturn> returnFindById(String member_id) 
	{
		List<BookReturn> bookReturn = null;
		try {
			bookReturn = bookReturnService.returnFindById(member_id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return bookReturn;
	}
	
}
