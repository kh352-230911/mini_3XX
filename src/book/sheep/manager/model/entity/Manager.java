package book.sheep.manager.model.entity;


/**
 * 2023-11-05
 * @author 고혜진
 * tb_manager
 */
public class Manager 
{
	private String manager_id;  //매니저 아이디
	private String manager_pw;  //매니저 패스워드
	private boolean is_manager; //매니저인지 아닌지 체크
	//private int manager_no;     //매니저 사번->아이디가 pk라 추후에 빼도 될거같다.
	
	public Manager() {}
	
	public Manager(String manager_id, String manager_pw, boolean is_manager)
	{
		this.manager_id = manager_id;
		this.manager_pw = manager_pw;
		this.is_manager = is_manager;
		//this.manager_no = manager_no;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_pw() {
		return manager_pw;
	}

	public void setManager_pw(String manager_pw) {
		this.manager_pw = manager_pw;
	}

	//get
	public boolean isIs_manager() {
		return is_manager;
	}

	//set
	public void setIs_manager(boolean is_manager) {
		this.is_manager = is_manager;
	}

//	public int getManager_no() {
//		return manager_no;
//	}
//
//	public void setManager_no(int manager_no) {
//		this.manager_no = manager_no;
//	}

	@Override
	public String toString() {
		return "Manager [manager_id=" + manager_id + ", manager_pw=" + manager_pw + ", is_manager=" + is_manager
				+ "]";
	}
	
	

}
