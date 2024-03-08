package com.example.demo.domain.post.svc;

import com.example.demo.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostSVC {

  //목록
  List<Post> findAll();

  List<Post> findAll(Long reqPage, Long recCnt);

  //등록
  Long save(Post post);

  //조회
  Optional<Post> findById(Long postId);

  // 단건삭제
  int deleteById(Long postId);

  //여러건삭제
  int deleteByIds(List<Long> postIds);

  //수정
  int updateById(Long postId, Post post);

  //총레코드 건수
  int totalCount();
}
