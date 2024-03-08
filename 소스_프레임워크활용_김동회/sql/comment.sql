-- 테이블 삭제
drop table comments;

-- 시퀀스 삭제
drop  sequence comments_comments_id_seq;

---------
--댓글관리
--------
create table comments(
    comments_id  number(10),
    post_id   number(10),
    detail       CLOB

);
--기본키
alter table comments add constraint comments_comments_id_pk primary key(comments_id);

ALTER TABLE comments
ADD CONSTRAINT fk_post_id
FOREIGN KEY (post_id)
REFERENCES post(post_id);

--시퀀스생성
create sequence comments_comments_id_seq;

--생성--
insert into comments(comments_id,post_id,detail)
     values(comments_comments_id_seq.nextval, 128 , '55555555555555555555555');
insert into comments(comments_id,post_id,detail)
     values(comments_comments_id_seq.nextval, 128 , '66666666666666666666666');
insert into comments(comments_id,post_id,detail)
     values(comments_comments_id_seq.nextval, 129 , '7777777777777777777777');
insert into comments(comments_id,post_id,detail)
     values(comments_comments_id_seq.nextval, 133 , '88888888888888888888');



     commit;