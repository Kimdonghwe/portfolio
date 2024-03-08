package com.example.demo.web;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.member.svc.MemberSVC;
import com.example.demo.web.form.login.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping
public class LoginController {

  private final String SESSIONDATA = "UserData";
  @Autowired
  MemberSVC memberSVC;


  @GetMapping("/login")
  public String loginFrom() {

    return "login";
  }

  @PostMapping("/login")
  public String login(LoginForm loginForm, Model model,
                      HttpServletRequest request,
                      @RequestParam(value = "redirectUrl",required = false) String redirectUrl){

    //1) 유효성 체크
    String pattern = "^[a-zA-Z0-9._%+-]{1,20}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    if (!Pattern.matches(pattern, loginForm.getEmail())) {
      model.addAttribute("s_err_email","이메일 양식을 지켜주세요");
      return "login";
    }

    // 1) 유효성체크 - 비밀번호
    pattern = "^[A-Za-z0-9@]{6,12}$";
    if (!Pattern.matches(pattern, loginForm.getPasswd())) {
      model.addAttribute("s_err_passwd","비밀번호 양식을 지켜주세요");
      return "login";
    }

    //2) 회원 유무체크
    //2-1) 회원 아이디 존재유무 체크

    if (memberSVC.existMemberId(loginForm.getEmail())) {
      Optional<Member> optionalMember = memberSVC.findByEmailAndPasswd(loginForm.getEmail(),loginForm.getPasswd());
      if(optionalMember.isPresent()){  //회원정보가 있는경우
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSIONDATA, optionalMember.get());
        return redirectUrl !=null ? "redirect:"+redirectUrl : "redirect:/";
    }
      else{  //회원정보가 없는경우
        model.addAttribute("s_err_email","이메일이나 비밀번호가 틀립니다.");
        return "login";
      }

    }

    
    return "login";
  }

  //로그아웃
  @ResponseBody
  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {

    String flag = "NOK";
    //세션이 있으면 가져오고 없으면 생성하지 않는다.
    HttpSession session = request.getSession(false);

    //세션 제거
    if(session !=null ){
      session.invalidate();
      flag ="OK";
    }
    return flag;
  }
}
