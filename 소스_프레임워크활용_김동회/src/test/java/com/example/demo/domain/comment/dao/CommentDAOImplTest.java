package com.example.demo.domain.comment.dao;

import com.example.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class CommentDAOImplTest {

  @Autowired
  CommentDAO commentDAO;

  @Test
  void findbyIdAll() {

    List<Comment>  comments = commentDAO.findbyIdAll(128L);

    log.info("comments = {} ", comments);
  }

  @Test
  void tototalbyIdCnt() {
  }

  @Test
  @DisplayName("댓글추가")
  void addComment() {
    Long n=115L;
    for(int i=1; i<=115; i++){
      Long commentsId = commentDAO.addComment(n,"user1@kh.com","NO " + i);
    }
  }

  @Test
  void testFindbyIdAll() {

    List<Comment> comments = commentDAO.findbyIdAll(115L, 2l, 10l);

    comments.forEach(ele -> log.info("ele = {}", ele) );
  }

  @Test
  @DisplayName("댓글삭제")
  void delComment() {

    log.info("comments delete is true? {}", commentDAO.delComment(13L));
  }

  @Test
  @DisplayName("댓글수정")
  void modifyComment() {

    log.info("comments modify is true? {}", commentDAO.modifyComment(55l,"페르소나 5 ㅈ 꿀잼") );
  }

  @Test
  void delCommentByPid() {

    log.info("comments delete is true? {}", commentDAO.delCommentByPid(115L));
  }
}