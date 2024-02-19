--테이블삭제
drop table post;

--시퀀스삭제
drop sequence post_post_id_seq;



create table post(
    POST_ID         NUMBER,
    TITLE           varchar(30),
    detail          CLOB,
    PNAME           VARCHAR(20),
    cdate           timestamp, --생성일시
    udate           timestamp  --수정일시
);
--기본키
alter table POST add constraint POST_ID_PK primary key(POST_ID);



--시퀀스생성
create sequence post_post_id_seq;

--디폴트
alter table POST modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table POST modify udate default systimestamp; --운영체제 일시를 기본값으로


INSERT INTO POST(POST_ID,TITLE,DETAIL,PNAME)
            VALUES(post_post_id_seq.nextval,'A','11','홍길동');
INSERT INTO POST(POST_ID,TITLE,DETAIL,PNAME)
            VALUES(post_post_id_seq.nextval,'A','22','홍길순');
INSERT INTO POST(POST_ID,TITLE,DETAIL,PNAME)
            VALUES(post_post_id_seq.nextval,'A','33','홍길지');

COMMIT;
