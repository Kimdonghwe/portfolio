package com.example.demo.domain.comment.svc;


import com.example.demo.domain.entity.Comment;

import java.util.List;


public interface CommentSVC {

  public List<Comment> findbyIdAll(Long postId);

  public List<Comment> findbyIdAll(Long postId, Long reqPage, Long reqCnt);

  public  Integer tototalbyIdCnt(Long postId);

  public Long addComment(Long postId,String email, String detail);

  public Boolean delComment(Long commentsId);

  public Boolean delCommentByPid(Long postId);


  public Boolean modifyComment(Long commentsId, String detail);

}
