package com.example.demo.web;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.member.svc.MemberSVC;
import com.example.demo.web.form.member.JoinForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

  @Autowired
  MemberSVC memberSVC;

  @GetMapping("/join")
  public String joinForm(){

    return "member/joinForm";
  }

  @PostMapping("/join")
  public String join(JoinForm joinForm,
                     Model model){

    log.info("joinForm = {}", joinForm);

    // 1) 유효성체크 - 이메일
    String pattern = "^[a-zA-Z0-9._%+-]{1,20}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    if (!Pattern.matches(pattern, joinForm.getEmail())) {
      model.addAttribute("s_err_email","이메일 양식을 지켜주세요 ex) abc@kh.com");
      return "member/joinForm";
    }

    // 1) 유효성체크 - 비밀번호
     pattern = "^[A-Za-z0-9@]{6,12}$";
    if (!Pattern.matches(pattern, joinForm.getPasswd())) {
      model.addAttribute("s_err_passwd","영문/숫자 6~12자리");
      return "member/joinForm";
    }

    // 1) 유효성체크 - 닉네임
     pattern = "^[a-zA-Z0-9가-힣!@#$%^&*()-_=+\\\\|\\[\\]{};:'\",.<>/?]{2,10}$";
    if (!Pattern.matches(pattern, joinForm.getNickname())) {
      model.addAttribute("s_err_nickname","영문/숫자/한글/_-가능, 3~10자리");
      return "member/joinForm";
    }

    //회원 등록
    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);
    Long memberId = memberSVC.insertMember(member);
    return "redirect:/";
  }
}
