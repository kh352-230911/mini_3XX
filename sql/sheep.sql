/*
231109 
테스트용 아이디 
nsert into tb_member values('test','1', '박자두', '01092947364', 0);

231108 pm9:50~
수정사항
1.전체적으로 중복되는 코드 제거
2.각 테이블에 주석 추가/수정
3.성은씨가 보내준 zip파일에서 request 테이블 관련 추가/수정
4.그 동안 파일을 취합하지 못해서 수정사항이 많을 것 같습니다.
만약 오류가 난다면 피드백 부탁드립니다.

list
1.회원테이블
2.관리자테이블
3.도서 정보 테이블 -시퀀스
4.도서 대여 정보 테이블-시퀀스-트리거
5.도서 반납 정보 테이블 
=>대여에서 사용자가 반납을 했을 경우를 위한 테이블이었는데 큰 난관이 발생..
부모-자식 참조 관계 테이블 설계를 적절하게 매칭하지 못하여 삭제고려중.
6.도서 연체 테이블 
=>반납과 더불어 사용 하지 못할 거서 같음.
7.공지 테이블-시퀀스
8.리뷰 테이블
=>사용하지 못할 것 같아 주석처리/실제 프로젝트에서도 사용안함
9.도서 요청 테이블-시퀀스

*/
--231106
--231105
-- (system계정) sheep 계정 생성 
-- sheep/sheep

alter session set "_oracle_script" = true;
create user sheep
identified by sheep
default tablespace users;
grant connect, resource to sheep;
alter user sheep quota unlimited on users;
--========================================================
/* 
1.회원 테이블
member_id : 회원 아이디 pk
member_pw : 비밀번호
member_name:회원 이름 
member_phone:회원 전화번호 uq 
is_manager : 관리자인지 확인하기 위한 컬럼값 일반회원은 기본값 0(false).
*/
create table tb_member (
    member_id varchar2(30),
    member_pw varchar2(30) not null,
    member_name varchar2(20) not null,
    member_phone varchar2(30) not null,
    is_manager integer default 0 ,
    constraints pk_tb_member_member_id primary key(member_id),
    constraints uq_tb_member_member_phone unique(member_phone)
);
desc tb_member;
select * from tb_member;
delete from tb_member;
-- member정보
insert into tb_member values('hoggd','1234','홍길동','01012345678', 0);
insert into tb_member values('sinsa','6285', '신사임당', '01012121234', 0);
insert into tb_member values('bogo','4567', '장보고', '01034562543', 0);
insert into tb_member values('haribo','6346', '하리보', '01096857493', 0);
insert into tb_member values('jingnie','2345', '진지니', '01057486079', 0);
insert into tb_member values('ori','3425', '곽오리', '01082947364', 0);

commit;

--1109 join test
select
   m.member_id, m.member_phone, r.return_no, r.book_title, r.rental_start , r.rental_end, r.return_date 
from 
    tb_member m left join tb_book_return r 
    on m.member_id = r.member_id;
--group by m.member_id, r.return_no;
--------------------------------------------------------------------------------------------------------------------------------------------
--drop table tb_book_info;
--drop table tb_manager;
--drop table tb_member;
--drop table tb_notice;
--drop table tb_book_rental;
--drop table tb_book_request;
--drop table tb_book_return;
--drop table tb_review;
--drop table tb_book_overdue_info;
----------------------------------------------------------------------------------------------------------------------------------------------
/* 
2.관리자 테이블
manager_id : 매니저 아이디 pk
manager_pw : 비밀번호
is_manager: 매니저인지 확인하기 위한 컬럼값 기본1
manager_no:매니저 사번 uq 값, 1108 쓰임새가 없어보여 삭제함
is_manager : 관리자인지 확인하기 위한 컬럼값 일반회원은 기본값 0(false).
*/
create table tb_manager (
    manager_id varchar2(50) not null, 
    manager_pw varchar2(50) not null,
    is_manager number default 1 not null,
    constraints pk_tb_maneger_manager_id primary key(manager_id),
    constraints ck_tb_manager_manager check(is_manager in ('1', '0'))
);
--drop table tb_manager;
select * from tb_manager; 
select * from tb_member; 
desc tb_manager;
-- manager정보 아래의 3XX,testManager 중 아무거나 insert
insert into tb_manager values('3XX', 'XXX', 1);
insert into tb_manager values('testManager', '1234', 1);
commit;
----------------------------------------------------------------------------------------------------------------
/*3.책 정보 테이블
book_no: 각 책마다의 고유 넘버값 pk
book_title: 책 제목
book_author: 책 저자
book_publisher: 출판사
book_is_rental:책 대여 여부 =>새로 추가 시, 기본값 1(true) 책을 누군가가 대여한 상태면 0(false)
booktype컬럼은 초기 계획때 장르로 나눠 사용해보려했지만 삭제함.
*/
create table tb_book_info (
    book_no number not null, -- pk
    book_title varchar2(300) not null,
    book_author varchar2(100) not null,
    book_publisher varchar2(100) not null,
    book_is_rental number default 1 not null,
    constraints pk_tb_book_book_no primary key(book_no),
    constraints ck_tb_book_info_book_is_rental check(book_is_rental in ('1', '0'))
);
select * from tb_book_info order by book_no; -- 확인
desc tb_book_info;

-- book_info에 추가할 책 정보  - 책넘버pk / 제목 / 저자 / 출판사 / 대여가능여부
insert into tb_book_info(book_no, book_title, book_author,  book_publisher,book_is_rental) values (seq_book_no.nextval, '기분이 태도가 되지 말자','김수현','하이스트',1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '오늘도 나아가는 중입니다','조민', '참새책방', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '불안','알랭 드 보통','은행나무', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '비가 오면 열리는 상점', '유영광', '클레이하우스', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '불편한 편의점', '김호연', '나무옆의자', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '불편한 편의점2', '김호연', '나무옆의자', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '꽃을 보듯 너를 본다', '나태주', '지혜', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '내가 아직 쓰지 않은것', '강신애', '문학동네', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '도둑맞은 집중력', '요한 하리', '어크로스', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '나는 생각이 너무 많아', '크리스텔 프티콜랭', '부키', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '물고기는 존재하지 않는다', '룰루 밀러', '곰출판', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values (seq_book_no.nextval, '당신의 꿈은 우연이 아니다', '안토니오 자드라', '추수밭', 1);
insert into tb_book_info(book_no, book_title, book_author,  book_publisher, book_is_rental) values 
(seq_book_no.nextval, '농담곰의 여유만만 간단 여행 영어회화', '나가노', '소미미디어 ',1);
insert into tb_book_info(book_no, book_title, book_author, book_publisher, book_is_rental) values 
(seq_book_no.nextval, '농담곰13', '나가노', '소미미디어 ', 1);
insert into tb_book_info(book_no, book_title, book_author, book_publisher, book_is_rental) values 
(seq_book_no.nextval, '먼작귀 특별판3', '나가노', '대원씨아이 ', 1);

/*
3.1북인포 시퀀스(book_no값을 순차적으로 /중복 없이 발급해줌.)
*/
create sequence seq_book_no
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
cache 20;
--drop sequence seq_book_no;
------------------------------------------------------------------------------------------------------------------
/*
4.도서 대여 정보 테이블
rental_no : 대여 고유 넘버값pk
member_id : 대여한 회원 아이디
book_no : 대여한 책 고유 값
book_ title : 책 제목
rental_start : 대여 시작일(sysdate)
rental_end : 대여 종료일(sysdate+4) 기본적으로 시작일 +4일
book_is_rental : 대여 가능 여부. 대여를 하면 1(true,가능)->0(false,불가능)으로 값 변경
*/
create table tb_book_rental (  
   rental_no number not null,
   member_id varchar2(30) not null,
   book_no number not null,
   book_title varchar2(200) not null,
   rental_start date default sysdate not null, 
   rental_end date  default sysdate + 4 not null,
   book_is_rental number(1) default 0 not null
 );
Alter table tb_book_rental ADD CONSTRAINT pk_tb_book_rental_rental_no PRIMARY KEY(rental_no);
--constraints pk_tb_book_rental_rental_no primary key (rental_no),
Alter table tb_book_rental ADD CONSTRAINT fk_tb_book_rental_memeber_id 
foreign key (member_id) references tb_member (member_id) on delete set null;
--constraints fk_tb_book_rental_memeber_id foreign key (member_id) references tb_member (member_id)
Alter table tb_book_rental ADD CONSTRAINT fk_tb_book_rental_book_no 
 foreign key (book_no) references tb_book_info(book_no) on delete set null;
-- constraints fk_tb_book_rental_book_no foreign key (book_no) references tb_book_info(book_no) on delete set null,
Alter table tb_book_rental ADD constraints ck_tb_book_rental_book_is_rental check ( book_is_rental in ('1','0'));

drop table tb_book_rental;
desc tb_book_rental;
select * from tb_book_rental order by rental_no;
select *from tb_book_info;
--1107 대여번호 시퀀스 Sequence SEQ_BOOK_RENTAL_NO이(가) 생성되었습니다.
create sequence seq_book_rental_no
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
cache 20;
drop sequence seq_book_rental_no;

commit;

--1107 회원테이블과 대여테이블 조인 테스트
select member_id,rental_no, book_title,rental_start,rental_end from tb_member left join tb_book_rental using(member_id) where member_id='sinsa';
--현재 left join으로 보여줬을 때
--회원 아이디 비밀번호 전화번호 이름 매니저인지? 대여 고유번호, 책번호 책 제목 대여 시작일 대여 종료일 
--회원 입장에서 보여줘야할 것 
--대여 고유 번호, 책 제목, 대여 시작일, 대여 종료일

/*5. 책 반납정보 테이블 : tb_book_return
create table tb_book_return (
   return_no number not null,
   member_id varchar2(30)  not null,
   book_no number  not null,
   book_title varchar2(30)  not null,
   retal_end date not null,
   real_return_date date default sysdate not null,
   book_is_rental number(1) default 1 not null,
   
   constraints pk_tb_book_return_return_no primary key (return_no),
   constraints fk_tb_book_return_member_id foreign key (member_id) references tb_member (member_id)
                       on delete set null,
  constraints fk_tb_book_return_book_no foreign key (book_no) references tb_book_info(book_no) 
                     on delete set null,
--   constraints fk_tb_book_rental_book_title foreign key (book_title) references tb_book_info(book_title)
--                    on delete set null ,  --book_title : not uq,not pk
   constraints ck_tb_book_return_book_is_rental check ( book_is_rental in ('1','0'))
);*/



--6. 책 연체정보 테이블 : TB_BOOK_OVERDUE_INFO
--create table TB_BOOK_OVERDUE_INFO (
--  overdue_no number not null,
--  member_id varchar2(30) not null,
--  overdue_period date  not null,  --연체기간,,
--  is_overdue number(1) default 1 not null,
--  
--  constraints pk_tb_book_overdue_info_overdue_no primary key (overdue_no),
--  constraints fk_tb_book_overdue_info_member_id foreign key (member_id) references tb_member (member_id)
--                       on delete set null,
--  constraints ck_tb_book_overdue_info_is_overdue check (is_overdue in ('1','0'))                     
--);

-----------------------------------------------------------------------------------------------------------
/*
7.공지 테이블 (회원,비회원은 읽기만 가능하고 추가/삭제는 관리자만 가능)
notice_no:공지번호 pk
manager_id:관리자 아이디
notice_content:공지 내용
notice_pw: 공지 비밀번호 (각 글의 비밀번호)
notice_date:공지 날짜 ,기본값 당일
is_manager:관리자인지 확인
*/
create table tb_notice (
    notice_no number, --pk
    manager_id varchar2(30) not null, --tb_manager의pk를 참조하는 fk 
    notice_content varchar2(1000) not null,
    notice_pw varchar2(30) not null,
    notice_date date default sysdate,
    is_manager number default 0 , --tb_manager의 fk - fk로 참조 못함.(pk,uq아니라서)
    constraints pk_tb_notice_notice_no primary key(notice_no),
    constraints fk_tb_notice_manager_id foreign key(manager_id) references tb_manager(manager_id) on delete cascade
);
/*
7.1 notice_no 시퀀스
*/
create sequence seq_notice_no
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
cache 20;
--drop  sequence seq_notice_no;
insert into tb_notice values (seq_notice_no.nextval,'testManager','231106 금일부터 도서 대여 서비스 시범 운영 합니다.','1234',default,1);
insert into tb_notice values (seq_notice_no.nextval,'testManager','대여한 도서를 깨끗하게 사용해주시기 바랍니다.','5678',default,1);
select * from tb_notice;
commit;

/*8. TB_REVIEWE 리뷰 게시판 테이블
create table tb_review (
    review_no number, --pk
    member_id varchar2(30), --fk
    rental_no number, --fk
    book_title varchar2(30), --fk
    reviewe_content varchar2(1000) not null,
    notice_pw varchar2(30) not null,
    notice_date date default sysdate,
    manager_id varchar2(30),

    constraints pk_tb_review_review_no primary key(review_no),
    constraints fk_tb_review_member_id foreign key(member_id) references tb_member(member_id) on delete cascade,
    constraints fk_tb_review_rental_no foreign key(rental_no) references tb_book_rental(rental_no) on delete cascade,
    --constraints fk_tb_review_book_title foreign key(book_title) references tb_book_info(book_title) on delete cascade,
    constraints fk_tb_review_manager_id foreign key(manager_id) references tb_manager(manager_id) on delete cascade
);
*/
----------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
9. TB_BOOK_REQUEST 도서 요청 테이블
*/
create table tb_book_request (
    request_no number, --pk
    member_id varchar2(30), --fk
    request_book_title varchar2(300) not null,
    request_book_author varchar2(100) not null,
    request_book_publisher varchar2 (100) not null,
    constraints tb_book_request_request_no primary key(request_no),
    constraints fk_tb_notice_member_id foreign key(member_id) references tb_member(member_id) on delete cascade
);
--drop table tb_book_request;
/*
9.1도서 요청 테이블 시퀀스 생성
*/
create sequence seq_book_request_no
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
cache 20;
--drop sequence seq_book_request_no;


-------------------------------------------------------------------------------------------------------------
--책이 대여되면 tb_book_info에 해당 책의 BOOK_IS_RENTAL이 1->0으로 바뀌도록 트리거 테스트
--1108 : 대여하고 반납할때마다 즉, book_rental에 대여정보가 추가/삭제 되는 경우 책 대여 여부도 0<->1로 바꿔주는 트리거.
--1108 저녁 :  insert할 때 book_info의 대여값 0 / delete할 때 대여값을 1로 하여 대여 가능으로 처리.
drop trigger trig_update_book_rental_state;
create or replace trigger trig_update_book_rental_state
    after 
    insert or delete on tb_book_rental --해당 테이블에 insert[대여함] , delete[반납함]이 이루어 졌을 때
    for each row
begin
    if inserting then 
   -- if : new.book_is_rental= 0 then
        update 
            tb_book_info
        set
            book_is_rental = 0 
        where
            book_no = :new.book_no;
    --end if;
    
     elsif deleting then
  --    if : old.book_is_rental= 0 then
        update 
            tb_book_info
        set
            book_is_rental = 1 
        where
            book_no = :old.book_no;
  --  end if;
 end if;
end;
/
drop table tb_book_rental;
drop table tb_book_info;
select * from tb_book_info;
select * from tb_book_rental;
commit;

-----------------------------------------------------------------------------------------------------------------------------
/*
1109 tb_book_rental에서 delete 되는 경우, 즉 사용자가 직접 반납하는 경우에 
tb_book_return 이라는 테이블에 insert한다.
return테이블은 rental 테이블과 컬럼구조는 동일하고 
삭제된 컬럼:book_is_rental
추가된 컬럼:return_date 실제 반납일(default sysdate) 
*/
drop table tb_book_return;

create table tb_book_return (  
   return_no number not null,
   member_id varchar2(30) not null,
   book_no number not null,
   book_title varchar2(200) not null,
   rental_start date not null, 
   rental_end date not null,
   return_date date default sysdate not null,
   constraints pk_tb_book_return_return_no primary key (return_no)
 );
 select * from tb_book_return;

--return 테이블에 insert하는 트리거
--트리거 생성 return table에 총 7개의 값이 들어가야한다.
create or replace trigger trig_tb_book_return
    after
    delete on tb_book_rental 
    for each row
begin
          insert into
                    tb_book_return(return_no, member_id, book_no, book_title , rental_start ,rental_end, return_date)
            values (
                    seq_book_return_no.nextval,
                    :old.member_id,
                    :old.book_no,
                    :old.book_title,
                    :old.rental_start,
                    :old.rental_end,
                    sysdate
                    );
end;
/
/*
1109 return table의 시퀀스
*/
create sequence seq_book_return_no
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
cache 20;
drop  sequence seq_book_return_no;


-----------------------------------------------------------------1109 test query ----------------------------------------------------------
select * from tb_book_return where member_id='jin';
select trunc(return_date)-trunc(rental_end) "날짜 차이" from tb_book_return; 
--시간부분을 trunc로 날려버리고 단순날짜로만 계산.

select  from tb_book_return;
/*
실제 반납일-반납종료일 = 음수~0이면 정상반납
                                 = 1이상의 양수면 연체 
*/
--UPDATE [테이블] SET [열]= '변경할값' WHERE [열] is null
update 
    tb_book_return 
set 
    return_date = rental_end --+13/24
where 
    return_no = 2;

  
commit;
select * from tb_book_rental;


select m.member_id, m.member_name, m.member_phone, r.book_no, r.book_title, r.rental_end from  tb_member m left join tb_book_rental r on m.member_id = r.member_id;