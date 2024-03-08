package com.example.demo.domain.member.svc;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.member.dao.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberSVCImpl implements  MemberSVC{

  @Autowired
  MemberDAO memberDAO;

  @Override
  public Long insertMember(Member member) {
    return memberDAO.insertMember(member);
  }

  @Override
  public boolean existMemberId(String email) {
    return memberDAO.existMemberId(email);
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email,passwd);
  }
}
