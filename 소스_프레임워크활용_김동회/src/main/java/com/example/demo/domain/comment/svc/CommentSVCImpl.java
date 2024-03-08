package com.example.demo.domain.comment.svc;


import com.example.demo.domain.comment.dao.CommentDAO;
import com.example.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentSVCImpl implements CommentSVC{

  @Autowired
  CommentDAO commentDAO;
  @Override
  public List<Comment> findbyIdAll(Long postId) {
    return commentDAO.findbyIdAll(postId);
  }

  @Override
  public List<Comment> findbyIdAll(Long postId, Long reqPage, Long reqCnt) {
    return commentDAO.findbyIdAll(postId, reqPage, reqCnt);
  }

  @Override
  public Integer tototalbyIdCnt(Long postId) {
    return commentDAO.tototalbyIdCnt(postId);
  }

  @Override
  public Long addComment(Long postId,String email,String detail) {
    return commentDAO.addComment(postId,email,detail);
  }

  @Override
  public Boolean delComment(Long commentsId) {
    log.info("Service commentId = {}" , commentsId);
    return commentDAO.delComment(commentsId);
  }

  @Override
  public Boolean delCommentByPid(Long postId) {
    return commentDAO.delCommentByPid(postId);
  }

  @Override
  public Boolean modifyComment(Long commentsId, String detail) {
    return commentDAO.modifyComment(commentsId,detail);
  }
}
