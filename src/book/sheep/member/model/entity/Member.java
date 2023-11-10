package book.sheep.member.model.entity;

public class Member {
	private String member_id;
	private String member_pw;
	private String member_phone;
	private String member_name;
	private boolean is_manager;
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(String member_id, String member_pw, String member_phone, String member_name, boolean is_manager) {
		super();
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_phone = member_phone;
		this.member_name = member_name;
		this.is_manager = is_manager;
	}

	
	//11.09 대여테이블과 조인하기 위해서 만든 파라미터 생성자
		public Member(String member_id, String member_phone, String member_name) {
			this.member_id = member_id;
			this.member_phone = member_phone;
			this.member_name = member_name;
		}
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public boolean isIs_manager() {
		return is_manager;
	}

	public void setIs_manager(boolean is_manager) {
		this.is_manager = is_manager;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", member_pw=" + member_pw + ", member_phone=" + member_phone
				+ ", member_name=" + member_name + ", is_manager=" + is_manager + "]";
	}
	
}