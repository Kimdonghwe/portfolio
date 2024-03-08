package com.example.demo.domain.member.dao;

import com.example.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {

  //회원 등록
  public Long insertMember(Member member);

  //회원 아이디 조회
  public boolean existMemberId(String email);

  // 회원 조회

  public Optional<Member> findByEmailAndPasswd(String email, String passwd);

  // 회원 수정

  // 회원 탈퇴
}
