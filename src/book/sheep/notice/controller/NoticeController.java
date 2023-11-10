package book.sheep.notice.controller;

import java.util.List;

import book.sheep.notice.model.entity.Notice;
import book.sheep.notice.model.service.NoticeService;

public class NoticeController 
{
	private NoticeService noticeService = new NoticeService();
	
	public List<Notice> findAllNotice() 
	{
		List<Notice> notices = null;
		try {
			notices = noticeService.findAllNotice();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return notices;
	}


	//1106 공지 추가.
	public int insertNotice(Notice notice) 
	{
		System.out.println("noticeController-insertNotice");
		//notice 값 확인
		System.out.println("notice.getNotice_content():"+notice.getNotice_content());
		int result=0;
		try 
		{
			result = noticeService.insertNotice(notice);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("불편을 드려 죄송합니다."+e.getMessage());
		}
		return result;
	}


	
	//1107 공지 삭제.
	public int deleteNotice(Notice notice) 
	{
		int result = 0;
		try
		{
			result = noticeService.deleteNotice(notice);
		}
		catch(Exception e)
		{
			//1.예외로그 출력
			e.printStackTrace();
					
			//2.오류메시지 전송
			System.err.println("불편을 드려 죄송합니다."+e.getMessage());
		}
		return result;
	}
	
}
