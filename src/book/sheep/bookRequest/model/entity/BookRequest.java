package book.sheep.bookRequest.model.entity;

//이 클래스는, tb_book_reQeust와 직접적으로 매칭되는 클래스로서
/**
 * 1. table = entity class 그자체,
 * 2. 컬럼 = field 
 * 3. row = bookReqeust객체 하나하나,.
 **/
public class BookRequest {
   //일단 컬럼들을 필드로 선언하자.
   private int reqeustNo;
   private String memberId;
   private String reqeustBookTitle;
   private String reqeustBookAuthor;
   private String reqeustBookPublisher;
  
   //생성자 생성
   public BookRequest() {
	super();
   }

    public BookRequest(int reqeustNo, String memberId, String reqeustBookTitle, String reqeustBookAuthor,
		String reqeustBookPublisher) {
	super();
	this.reqeustNo = reqeustNo;
	this.memberId = memberId;
	this.reqeustBookTitle = reqeustBookTitle;
	this.reqeustBookAuthor = reqeustBookAuthor;
	this.reqeustBookPublisher = reqeustBookPublisher;
}
     
    
    //getter, setter 생성
	public int getReqeustNo() {
		return reqeustNo;
	}

	public void setReqeustNo(int reqeustNo) {
		this.reqeustNo = reqeustNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReqeustBookTitle() {
		return reqeustBookTitle;
	}

	public void setReqeustBookTitle(String reqeustBookTitle) {
		this.reqeustBookTitle = reqeustBookTitle;
	}

	public String getReqeustBookAuthor() {
		return reqeustBookAuthor;
	}

	public void setReqeustBookAuthor(String reqeustBookAuthor) {
		this.reqeustBookAuthor = reqeustBookAuthor;
	}

	public String getReqeustBookPublisher() {
		return reqeustBookPublisher;
	}

	public void setReqeustBookPublisher(String reqeustBookPublisher) {
		this.reqeustBookPublisher = reqeustBookPublisher;
	}

	//tostring 생성. 
	@Override
	public String toString() {
		return "bookReqeust [reqeustNo=" + reqeustNo + ", memberId=" + memberId + ", reqeustBookTitle="
				+ reqeustBookTitle + ", reqeustBookAuthor=" + reqeustBookAuthor + ", reqeustBookPublisher="
				+ reqeustBookPublisher + "]";
	}
   



} //class 괄호
