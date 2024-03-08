package com.example.demo.domain.post.svc;

import com.example.demo.domain.entity.Post;
import com.example.demo.domain.post.dao.PostDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostSVCImp implements PostSVC{

  @Autowired
  private PostDAO postDAO;

  PostSVCImp(PostDAO postDAO){
    this.postDAO=postDAO;
  }

  @Override
  public Long save(Post post) {
    return postDAO.save(post);
  }

  @Override
  public List<Post> findAll() {
    return postDAO.findAll();
  }

  @Override
  public List<Post> findAll(Long reqPage, Long recCnt) {
    return postDAO.findAll(reqPage,recCnt);
  }

  @Override
  public Optional<Post> findById(Long postId) {
    return postDAO.findById(postId);
  }

  @Override
  public int deleteById(Long postId) {

    log.info("SVC DELETE ID = {}", postId);
    return postDAO.deleteById(postId);
  }

  @Override
  public int deleteByIds(List<Long> postIds) {


    return postDAO.deleteByIds(postIds);
  }

  @Override
  public int updateById(Long postId, Post post) {
    return postDAO.updateById(postId, post);
  }

  @Override
  public int totalCount() {
    return postDAO.totalCount();
  }
}
