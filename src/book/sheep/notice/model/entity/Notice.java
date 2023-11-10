package book.sheep.notice.model.entity;

import java.sql.Date;

/**
 * 1106
 * @author 고혜진
 * tb_notice와 매칭되는 클래스
 *
 */
public class Notice 
{
	private int notice_no; //pk 
	private String manager_id; //fk 
	private String notice_content; 
	private String notice_pw;
	private Date notice_date; //default
	private boolean is_manager;

	
	//기본생성자
	public Notice()
	{
	}
	
	//매개변수 있는 생성자
	public Notice(int notice_no, String manager_id,String notice_content,
					String notice_pw,Date notice_date, boolean is_manager)
	{
		this.notice_no = notice_no;
		this.manager_id = manager_id;
		this.notice_content = notice_content;
		this.notice_pw = notice_pw;
		this.notice_date = notice_date;
		this.is_manager = is_manager;
	}

	public int getNotice_no() {
		return notice_no;
	}

	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public String getNotice_pw() {
		return notice_pw;
	}

	public void setNotice_pw(String notice_pw) {
		this.notice_pw = notice_pw;
	}

	public Date getNotice_date() {
		return notice_date;
	}

	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}

	public boolean isIs_manager() {
		return is_manager;
	}

	public void setIs_manager(boolean is_manager) {
		this.is_manager = is_manager;
	}
	
	
	
	
}
