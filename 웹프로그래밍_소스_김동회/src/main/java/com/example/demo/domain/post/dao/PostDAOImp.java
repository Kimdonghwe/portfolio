package com.example.demo.domain.post.dao;

import com.example.demo.domain.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class PostDAOImp implements PostDAO{


  private final NamedParameterJdbcTemplate template;

  PostDAOImp(NamedParameterJdbcTemplate template) {
    this.template = template;
  }


  // 게시글 등록
  @Override
  public Long save(Post post) {
    StringBuffer sql = new StringBuffer();

    sql.append("INSERT INTO POST(POST_ID,title,detail,pname) ");
    sql.append("VALUES(post_post_id_seq.nextval,:title,:detail, :pname) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(post);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"post_id","title","detail","pname"});
    Long post_id = ((BigDecimal)keyHolder.getKeys().get("post_id")).longValue();

    return post_id;
  }

  // 게시글 목록
  @Override
  public List<Post> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * ");
    sql.append("FROM POST ");
    sql.append("ORDER BY POST_ID DESC ");

    List<Post> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Post.class));

    return list;
  }

  //게시글 조회

  @Override
  public Optional<Post> findById(Long postId) {
    StringBuffer sql = new StringBuffer();

    sql.append("select title,pname,detail,udate ");
    sql.append("from post ");
    sql.append("where post_id = :postId");

    try{
      Map<String,Long> map = Map.of("postId",postId);
      Post post = template.queryForObject(sql.toString(),map,BeanPropertyRowMapper.newInstance(Post.class));
      return Optional.of(post);
    }
    catch (EmptyResultDataAccessException e){

      return Optional.empty();
    }

  }

  //단건삭제
  @Override
  public int deleteById(Long postId) {

    StringBuffer sql = new StringBuffer();
    sql.append("delete from post ");
    sql.append(" where post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("postId",postId);

    int deletedRowCnt = template.update(sql.toString(), param);
    return deletedRowCnt;
  }

  //여러건 삭제
  @Override
  public int deleteByIds(List<Long> postIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from post ");
    sql.append(" where post_id in (:postIds) ");

    Map<String,List<Long>> map = Map.of("postIds",postIds);
    int deletedRowCnt = template.update(sql.toString(), map);

    return deletedRowCnt;
  }

  //  수정
  @Override
  public int updateById(Long postId, Post post) {

    StringBuffer sql = new StringBuffer();

    sql.append("update post ");
    sql.append(" set title = :title, ");
    sql.append("    udate = default, ");
    sql.append("    detail = :detail ");
    sql.append(" where post_id =  :postId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("title", post.getTitle())
            .addValue("detail", post.getDetail())
            .addValue("postId", postId);

    log.info("post = {}, {}" , post.getTitle(), post.getDetail());
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }
}
