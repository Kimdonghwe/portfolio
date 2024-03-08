package com.example.demo.domain.member.svc;

import com.example.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {

  //회원 등록
  public Long insertMember(Member member);

  //회원조회 -이메일
  boolean existMemberId(String email);

  //회원조회 - (이메일,비밀번호)
  public Optional<Member> findByEmailAndPasswd(String email, String passwd);

  //회원정보수정

  //회원삭제

}
