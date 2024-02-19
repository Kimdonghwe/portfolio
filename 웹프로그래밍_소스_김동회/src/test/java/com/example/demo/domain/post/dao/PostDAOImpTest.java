package com.example.demo.domain.post.dao;

import com.example.demo.domain.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class PostDAOImpTest {

  @Autowired
  PostDAO postDAO;

  @Test
  @DisplayName("게시글작성")
  void save() {
    Post post = new Post();
    post.setTitle("sqld2");
    post.setDetail("111");
    post.setPname("홍길자");

    postDAO.save(post);
  }

  @Test
  void findAll() {
    List<Post> list = postDAO.findAll();

    list.forEach(ele -> log.info("post =  {} ", ele));
  }

  @Test
  void findById(){

     Optional<Post> optionalPost = postDAO.findById(43l);

     Post post = optionalPost.orElse(null);

     log.info("post = {} ", post);
  }

  @Test
  @DisplayName("단건삭제")
  void deleteById(){

    int deleteNum = postDAO.deleteById(2L);
    log.info("삭제건수 = {} ", deleteNum);

  }

  @Test
  @DisplayName("여러건삭제")
  void deleteByIds(){
    List<Long> pids = new ArrayList<>();
    pids.add(41L);
    pids.add(42L);
    pids.add(43L);
    int deleteNum = postDAO.deleteByIds(pids);
    log.info("삭제건수 = {} ", deleteNum);

  }

  @Test
  @DisplayName("수정")
  void updateById(){
    Post post = new Post();
    post.setTitle("호박고구마");
    post.setDetail("qeqwrqwr");

    int updateNum = postDAO.updateById(41L,post);

    log.info("수정건수 = {} ", updateNum);
  }
}