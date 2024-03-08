package com.example.demo.domain.member.dao;

import com.example.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  @DisplayName("회원등록")
  void insertMember() {
    Member member = new Member();

    member.setEmail("kys8634kys8634@gmail.com");
    member.setPasswd("abc1234");
    member.setNickname("abc");

    Long member_id = memberDAO.insertMember(member);

    log.info("member_id = {}", member_id);

  }

  @Test
  @DisplayName("이메일로 조회")
  void existMemberId() {

    String email = "user1@kh.com";

    boolean check = memberDAO.existMemberId(email);

    log.info("exist = {}" , check);
  }

  @Test
  @DisplayName("회원조회")
  void findByEmailAndPasswd() {
    String email = "user1@kh.com";
    String passwd = "user1234";

    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd(email,passwd);

    Assertions.assertThat(optionalMember).isPresent(); // Optional이 존재해야 함

//    Member findedMember = optionalMember.get();
//    Assertions.assertThat(findedMember.getEmail()).isEqualTo("user1@kh.com"); // 이메일 일치 여부 확인
//    Assertions.assertThat(findedMember.getPasswd()).isEqualTo("user1"); // 비밀번호 일치 여부 확인
  }
}