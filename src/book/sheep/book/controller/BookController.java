package book.sheep.book.controller;

import java.util.List;


import book.sheep.book.model.entity.Book;
import book.sheep.book.model.service.BookService;

/**
 * 2023-11-05
 * @author 고혜진
 * 
 *
 */
public class BookController 
{
	private BookService bookService = new BookService();
	
	public List<Book> findAllBook() {
		List<Book> books = null;
		try {
			books = bookService.findAllBook();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return books;
	}

	/**
	 * 1106 jin 책 제목으로 도서 검색.
	 */
	public List<Book> findBookByTitle(String bookTitle) 
	{
		List<Book> books = null;
		try 
		{
			books = bookService.findBookByTitle(bookTitle);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return books;
	}

	/**
	 * 1106 jin 책 저자명으로 도서 검색.
	 */
	public List<Book> findBookByAuthor(String bookAuthor) {
		List<Book> books = null;
		try 
		{
			books = bookService.findBookByAuthor(bookAuthor);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return books;
	}
	
	/**
	 * 1107 jin 책 출판사명으로 도서 검색.
	 */
	public List<Book> findBookByPublisher(String bookPublisher) 
	{
		List<Book> books = null;
		try 
		{
			books = bookService.findBookByPublisher(bookPublisher);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return books;
	}

	/**
	 * 1107 jin 도서 추가
	 * 
	 */
	public int insertBook(Book book) 
	{
		//System.out.println("bookController - insertBook");
		int result = 0;
		try 
		{
			result = bookService.insertBook(book);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다."+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 1107 jin 도서 삭제
	 * 
	 */
	public int deleteBook(Book book) 
	{
		//System.out.println("bookController - deleteBook");
		int result = 0;
		try 
		{
			result = bookService.deleteBook(book);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다."+e.getMessage());
		}
		return result;
	}
}
