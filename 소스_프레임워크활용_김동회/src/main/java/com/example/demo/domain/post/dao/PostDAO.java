package com.example.demo.domain.post.dao;

import com.example.demo.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
//  등록
  Long save(Post post);
  //조회
  //Optional 객체를 최대 1개를 저장할수 있는 컬렉션
  Optional<Post> findById(Long postId);

  //목록
  List<Post> findAll();

  List<Post> findAll(Long reqPage, Long recCnt);

  //단건삭제
  int deleteById(Long postId);
  //여러건삭제
  int deleteByIds(List<Long> postIds);
  //수정
  int updateById(Long postId, Post post);

  int totalCount();

}
