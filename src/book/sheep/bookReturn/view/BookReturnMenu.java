package book.sheep.bookReturn.view;

import static book.sheep.common.LoginTemplate.ReadUser;
import static book.sheep.common.LoginTemplate.ReadUserId;

import java.sql.Date;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import book.sheep.book.model.entity.Book;
import book.sheep.bookRental.controller.BookRentalController;
import book.sheep.bookRental.model.entity.BookRental;
import book.sheep.bookReturn.controller.BookReturnController;
import book.sheep.bookReturn.model.entity.BookReturn;

/**
 * @author 고혜진
 * 1108 책 반납 메뉴 
 * 
 * 1.해당 사용자의 대여 내용을 select로 뽑아온다.
 * selectMyBookRental = select * from tb_book_rental where member_id=?
 * 
 * 2.해당 테이블의 rental_state:1(대여중) / retal_state:0(대여종료=반납)
 * 
 * 3.대여중인 것만 선택해서 반납가능함.
 * 4.retal테이블에 있는 rental_no을 확인해 반납처리한다.
 * 5.===반납이 완료 되었습니다. 감사합니다.===
 * 
 */
public class BookReturnMenu 
{
	private BookReturnController bookReturnController = new BookReturnController();
	private BookRentalController bookRentalController = new BookRentalController();
	private Scanner sc = new Scanner(System.in);
	
	//1109오전 반납처리된 내역 확인 간단하게 toString으로 확인
	//1109오후 작동확인
	
	public void showReturnList()
	{
		List<BookReturn> bookReturn=null;
		bookReturn = bookReturnController.returnFindById(ReadUserId());
		displayMyBookReturns(bookReturn);
		
	}
	
	
	
	public void showBookReturnMenu()
	{
		//System.out.println("showBookReturnMenu");
		
		/* 아래에서 while문으로 반복한다.
		 *=================================
		 * 1.반납하기
		 * 0.뒤로가기
		 *=================================
		*/
		String menu = 
				"""
				====================================================================
				1.반납 하기
				0.이전 메뉴로 돌아가기
				====================================================================
				선택:""";
		String choice = null;
		int result=0;
		while(true)
		{
			List<BookRental> bookRentals = new BookRentalController().findAllMyBookRental(ReadUserId());
			displayMyBookRentals(bookRentals);
			System.out.println(menu);
			choice = sc.next();
			switch(choice)
			{
				case "1":
					//여기서 tb_book_return에 insert
					BookRental br = inputReturnBook(bookRentals);
					displayResult("대여 삭제",bookRentalController.deleteBookRental(br));
					//System.out.println("뭐여 왜 안돼냐................................");
					//result = bookRentalController.deleteBookRental(inputReturnBook(bookRentals));
					//result = bookReturnController.insertBookReturn(inputReturnBook(bookRentals));
					//displayResult("도서 반납",bookReturnController.insertBookReturn(br));
					break;
				case "0":
					return;
				default:
					System.out.println("잘못 선택하셨습니다.");
					break;
			}
		}
	}
	private void displayResult(String str, int result) 
	{
		if(result > 0) 
			System.out.println("도서 반납이 완료 되었습니다.");
			//System.out.println("🎉🎉 " + str + " 성공! 🎉🎉");
		//도서반납에 성공했으면 해당 rental_no와 동일한 데이터를 tb_book_rental에서 삭제해버린다.
		else
			System.out.println("도서 반납을 완료하지 못했습니다.");
			//System.out.println("😭😭 " + str + " 실패! 😭😭");				
	}
	
	//선택한 반납목록 1개를 가지고 반환함.
	private BookRental inputReturnBook(List<BookRental> bookRentals) 
	{
		System.out.print(">>반납하실 목록 번호를 입력해주세요..");
		int selectNum = sc.nextInt();//1
		System.out.println(">>선택한 반납 도서 제목:"+bookRentals.get(selectNum-1).getBook_title());
		return bookRentals.get(selectNum-1);
	}

	/**
	 * private int rental_no;
	private String member_id;
	private String book_no;
	private String book_title;
	private Date rental_start;
	private Date rental_end;
	//private boolean book_is_rental;
	private boolean rental_state;

	 */
	//내가 대여했던 내역을 모두 보여주는 메소드
	private void displayMyBookRentals(List<BookRental> bookRentals) 
	{
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.printf("%s \t%-40s \t%-15s \t%-15s \n", 
				"목록" , "책 이름", "대여 시작일", "대여 종료일");
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		if(bookRentals == null || bookRentals.isEmpty()) 
		{
			System.out.println("조회된 대여 결과가 없습니다..");
		}
		else {
			for(int i=0; i<bookRentals.size(); i++) 
			{
				System.out.printf("%d \t%-40s \t%-15s \t%-15s \n", 
						i+1,
						//bookRentals.get(i).getRental_no(), //대여 고유번호
						bookRentals.get(i).getBook_title(), //대여한 책 이름
						bookRentals.get(i).getRental_start(), //대여 시작일
						bookRentals.get(i).getRental_end()  //대여 종료일(시작일+4일)
						//bookRentals.get(i).isRental_state()? "대여중":"반납완료"
							);
			}
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * 1109 
	 * 반납리스트 보여주는 메소드
	 */
	private void displayMyBookReturns(List<BookReturn> bookReturns) 
	{
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.printf("%s \t%-40s \t%-10s \t%-10s \t%-10s \t%-10s\n", 
				"번호" , "책 이름", "대여 시작일", "대여 종료일", "실제 반납일", "연체일");
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		if(bookReturns == null || bookReturns.isEmpty()) 
		{
			System.out.println("조회된 반납 결과가 없습니다..");
		}
		else 
		{
			for(int i=0; i<bookReturns.size(); i++) 
			{
				 Period diff = Period.between(bookReturns.get(i).getReturn_date().toLocalDate(), 
						 bookReturns.get(i).getRental_end().toLocalDate());
			
				 System.out.printf("%d \t%-40s \t%-10s \t%-10s \t%-10s \t%-10s\n", 
						//i+1,
						bookReturns.get(i).getReturn_no(), //대여 고유번호
						bookReturns.get(i).getBook_title(), //대여한 책 이름
						bookReturns.get(i).getRental_start(), //대여 시작일
						bookReturns.get(i).getRental_end(), //대여 종료일(시작일+4일)
						bookReturns.get(i).getReturn_date(), //실제 반납일
						(diff.getDays()<0)? -(diff.getDays())+"일":"없음"
						//bookRentals.get(i).isRental_state()? "대여중":"반납완료"
							);
			}
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}
}
