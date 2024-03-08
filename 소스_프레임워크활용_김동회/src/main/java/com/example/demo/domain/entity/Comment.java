package com.example.demo.domain.entity;

import lombok.Data;

@Data
public class Comment {

  private Long commentsId;
  private Long postId;
  private String email;
  private String detail ;
}
