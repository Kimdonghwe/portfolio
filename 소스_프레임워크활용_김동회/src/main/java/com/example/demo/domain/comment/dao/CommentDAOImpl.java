package com.example.demo.domain.comment.dao;

import com.example.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository //dao역할을 하는 클래스
public class CommentDAOImpl implements CommentDAO{


  private NamedParameterJdbcTemplate template;

  CommentDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }


  //상품 조회시 해당 상품에 해당하는 댓글 호출
  @Override
  public List<Comment> findbyIdAll(Long postId) {

    StringBuffer sql = new StringBuffer();

    sql.append("select comments_id,post_id,email,detail from comments ");
    sql.append("where post_id = :postId");

    try {
      Map<String,Long> map = Map.of("postId",postId);
      List<Comment> comments =template.query(sql.toString(), map, BeanPropertyRowMapper.newInstance(Comment.class));
      return comments;

    }catch (EmptyResultDataAccessException e){
      //조회결과가 없는경우

      List<Comment> comments = new ArrayList<>();
      return  comments;
    }


  }

  //게시글 아이디에 해당하는 총 댓글수 반환
  @Override
  public Integer tototalbyIdCnt(Long postId) {
    StringBuffer sql = new StringBuffer();

    sql.append("select count(comments_id) from comments ");
    sql.append("where post_id = :postId ");

    MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("postId",postId);
    Integer byIdCnt =template.queryForObject(sql.toString(),parameterSource,Integer.class);
    return byIdCnt;

  }

  //게시글 작성
  @Override
  public Long addComment(Long postId,String email,String detail) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into comments(comments_id,post_id,email,detail) ");
    sql.append("values(comments_comments_id_seq.nextval, :postId, :email  , :detail ) ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("postId", postId)
            .addValue("detail", detail)
            .addValue("email", email);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"comments_id"});
    Long commentsId = ((BigDecimal)keyHolder.getKeys().get("comments_id")).longValue();


    return commentsId;
  }

  @Override
  public List<Comment> findbyIdAll(Long postId, Long reqPage, Long reqCnt) {

    StringBuffer sql = new StringBuffer();

    sql.append("select comments_id, post_id,email,detail from comments ");
    sql.append("where post_id = :postId ");
    sql.append("order by comments_id desc ");
    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    try {
      Map<String,Long> map = Map.of("postId",postId,"reqPage",reqPage,"recCnt",reqCnt);
      List<Comment> comments =template.query(sql.toString(), map, BeanPropertyRowMapper.newInstance(Comment.class));
      return comments;

    }catch (EmptyResultDataAccessException e){
      //조회결과가 없는경우

      List<Comment> comments = new ArrayList<>();
      return  comments;
    }
  }


//  댓글삭제 by 댓글아이디
  @Override
  public Boolean delComment(Long commentsId) {
    StringBuffer sql = new StringBuffer();

    sql.append("delete from comments ");
    sql.append("where comments_id = :commentsId ");

    log.info("Repository commentId = {}" , commentsId);
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("commentsId", commentsId);

    int delCnt = template.update(sql.toString(),param);
    log.info("DelCnt = {}", delCnt);
    return delCnt == 1 ? true : false ;
  }

  //  댓글삭제 by 게시글아이디
  @Override
  public Boolean delCommentByPid(Long postId) {
    StringBuffer sql = new StringBuffer();

    sql.append("delete from comments ");
    sql.append("where post_id = :postId ");
    
    int cnt = tototalbyIdCnt(postId);
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("postId", postId);

    int delCnt = template.update(sql.toString(),param);
    log.info("DelCnt = {}", delCnt);
    return delCnt == cnt ? true : false ;
  }

  // 댓글 수정


  @Override
  public Boolean modifyComment(Long commentsId, String detail) {

    StringBuffer sql = new StringBuffer();

    sql.append(" update comments ");
    sql.append(" SET detail = :detail ");
    sql.append(" where comments_id = :commentsId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("commentsId", commentsId)
            .addValue("detail",detail);

    int modifyCnt = template.update(sql.toString(),param);



    return modifyCnt == 1 ? true : false ;
  }
}
