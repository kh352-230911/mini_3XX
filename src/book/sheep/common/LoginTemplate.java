package book.sheep.common;

import java.util.HashMap;

/**
 * 1106 고혜진
 * 로그인 시 회원 정보(관리자포함)를 저장하는 hash map 
 * key:아이디 (String)
 * value:is_manager(boolean)
 */
import book.sheep.manager.model.entity.Manager;
import book.sheep.member.model.entity.Member;

public class LoginTemplate 
{
	static HashMap<String, Boolean> HashMap_loginInfo = new HashMap<String, Boolean>();
	
	public LoginTemplate() 
	{
		
	}

	public static void saveManager(Manager manager)
	{
		HashMap_loginInfo.put(manager.getManager_id(),manager.isIs_manager());
		//해쉬맵 값 확인해보기...
		ReadUser();
	}
	public static void saveMember(Member member)
	{
		HashMap_loginInfo.put(member.getMember_id(),member.isIs_manager());
		//해쉬맵 값 확인해보기...
		ReadUser();
	}
//	public static void saveMember(Member member)
//	{
//		HashMap_loginInfo.put(member.getMember_id(),member.Is_manager());
//		//해쉬맵 값 확인해보기...
//		ReadUser();
//	}
	
	public static HashMap<String, Boolean> ReadUser()
	{
		//System.out.println("manager or member - Read Manager");
		//해쉬맵 값 확인해보기...
		for (String key: HashMap_loginInfo.keySet()) {
			boolean value = HashMap_loginInfo.get(key);
			//System.out.println("" + key + ", Value:" + value);	
		}
		return HashMap_loginInfo;
	}
	
	//key값만 가져오는 메소드
	
	public static String ReadUserId()
	{
		String id=null;
		for (String key: HashMap_loginInfo.keySet()) 
		{
			id=key;
		}
		return id;
	}
	
	
	//관리자 로그아웃시 clear로 hashMap안의 값을 지워버린다.
	public static void RemoveUser()
	{
		//System.out.println("Remove user.Hm clear");
		//해쉬맵 값 삭제하기
		HashMap_loginInfo.clear();
		for (String key: HashMap_loginInfo.keySet()) {
			boolean value = HashMap_loginInfo.get(key);
			//System.out.println("" + key + ", Value:" + value);	
		}
	}
}
