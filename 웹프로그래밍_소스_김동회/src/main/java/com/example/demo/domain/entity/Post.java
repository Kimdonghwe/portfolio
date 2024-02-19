package com.example.demo.domain.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
  private Long postId;  //POST_ID         NUMBER
  private String title; //TITLE           varchar(30)
  private String detail; //detail          CLOB
  private String pname; //PNAME           VARCHAR(20)
  private LocalDateTime cdate;  //         timestamp
  private LocalDateTime udate;    //       timestamp
}
