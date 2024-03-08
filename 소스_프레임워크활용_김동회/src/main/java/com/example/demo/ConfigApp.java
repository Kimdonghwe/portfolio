package com.example.demo;

import com.example.demo.web.interceptor.LoginCheckInterCeptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigApp implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //인증 체크
    registry.addInterceptor(new LoginCheckInterCeptor())
            .order(1)                   //인터셉터 실행 순서 지정
            .addPathPatterns("/**")     //인터셉터에 포함시키는 url패턴, 루트부터 하위 경로 모두
            .excludePathPatterns(       //화이트리스트 등록
                    "/",                //초기화면
                    "/login",           //로긴처리
                    "/logout",          //로그아웃
                    "/member/join",
                    "/api/members/**",//회원가입
                    "/js/**"
            ); //인터셉터 제외 url패턴
  }


}
