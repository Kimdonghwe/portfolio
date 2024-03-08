package com.example.demo.web;

import com.example.demo.domain.comment.svc.CommentSVC;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.post.svc.PostSVC;
import com.example.demo.web.form.post.AddForm;
import com.example.demo.web.form.post.UpdateForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
// Controller 역할을 하는 클래스
@AllArgsConstructor
@Controller
@RequestMapping("/post")  // http://localhost:9080/post
public class PostController {

  private PostSVC postSVC;
  private CommentSVC commentSVC;

  //목록
  @GetMapping   // GET http://localhost:9080/post
  public String findAll( Model model
          ,
                         @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
                         @RequestParam(value = "reqCnt", defaultValue = "10") Long reqCnt,   // 레코드 수
                         @RequestParam(value = "cpgs", defaultValue = "1") Long cpgs,   //페이지 그룹 시작번호
                         @RequestParam(value = "cp", defaultValue = "1") Long cp
  )
  {
    List<Post> list = postSVC.findAll(reqPage,reqCnt);
    int totalCnt = postSVC.totalCount();

    log.info("cpgs = {}", cpgs);
    log.info("cp = {}", cp);
//
    model.addAttribute("list", list);
    model.addAttribute("totalCnt", totalCnt);
    model.addAttribute("cpgs", cpgs);
    model.addAttribute("cp", cp);

    return "post/all";
  }


  // 등록
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "post/add";
  }

  //등록처리
  @PostMapping("/add")
  public String addPost(
          AddForm addForm,
          Model model,
          RedirectAttributes redirectAttributes
  ) {
    String pattern_Title = "^[a-zA-Z0-9ㄱ-ㅎ가-힣_\\-\s]{1,15}$";
    //유효성 체크
    if(!Pattern.matches(pattern_Title,addForm.getTitle())){

      log.info("post1 = {} , {} , {}", addForm.getTitle(), addForm.getEmail(), addForm.getDetail());
      model.addAttribute("s_err_title","제목은 15자 이내여야 합니다 공백이 포함되서는 안됩니다.");
      return "post/add";
    }

    String pattern_Detail = "^[ㄱ-ㅎ가-힣a-zA-Z0-9`~!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?\s]{1,}$";
    if(!Pattern.matches(pattern_Detail,addForm.getDetail())){
      log.info("post3 = {} , {} , {}", addForm.getTitle(), addForm.getEmail(), addForm.getDetail());
      model.addAttribute("s_err_detail","1글자 이상 작성하세요");
      return "post/add";
    }
    Post post = new Post();
    post.setTitle(addForm.getTitle());
    post.setEmail(addForm.getEmail());
    post.setDetail(addForm.getDetail());

    log.info("post = {}", post);
    Long postId = postSVC.save(post);

    redirectAttributes.addAttribute("pid", postId);
    return "redirect:/post/{pid}/detail";
  }

  //조회                          // GET http://localhost:9080/post

  @GetMapping("/{pid}/detail")
  public String findById(@PathVariable("pid") Long postId
  ,Model model) {
    Optional<Post> findedpost = postSVC.findById(postId);
    Post post = findedpost.orElse(null);
    post.setPostId(postId);
    model.addAttribute("postData", post);

    model.addAttribute("postData",post);
    return "/post/detailForm";

    // 혀재 post 데이터와 해당 poet 에 대한 댓글 받아와서 객체로 감싼후 header, body 형태의 response json으로 만들어서 반환해서 정상적으로 출력시킴

    // 이제 받아온 json 파일을 가지고 게시글 화면을 만들어야한다.


  }



  //단건삭제
  @GetMapping("/{pid}/del")
  public String deleteById(@PathVariable("pid") Long postId){

    log.info("postId = {}", postId);


    commentSVC.delCommentByPid(postId);
    int deletedRowCnt = postSVC.deleteById(postId);
//    1)상품 삭제 -> 상품테이블에서 삭제



    return "redirect:/post";     // GET http://localhost:9080/products/
  }

  //여러건 삭제
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("pids") List<Long> pids){
    log.info("deleteByIds={}",pids);
    int deletedRowCnt = postSVC.deleteByIds(pids);
    return "redirect:/post";
  }

  // 수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm(@PathVariable("pid") Long postId,
                           Model model)
  {

    Optional<Post> optionalPost = postSVC.findById(postId);
    Post post = optionalPost.orElse(null);
    model.addAttribute("postData", post);
    return "post/updateForm";
  }

  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long postId,
                       UpdateForm updateForm,
                       RedirectAttributes redirectAttributes,
                       Model model
                       ){

    String pattern_Title = "^[a-zA-Z0-9ㄱ-ㅎ가-힣_\\-\s]{1,15}$";
    //유효성 체크
    if(!Pattern.matches(pattern_Title,updateForm.getTitle())){
      log.info("post1 = {}  , {}", updateForm.getTitle(),  updateForm.getDetail());
      model.addAttribute("postData", updateForm);
      model.addAttribute("s_err_title","제목은 15자 이내여야 합니다.");
      return "post/updateForm";
    }
    String pattern_Detail = "^[ㄱ-ㅎ가-힣a-zA-Z0-9`~!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?\s]{1,}$";
    if(!Pattern.matches(pattern_Detail,updateForm.getDetail())){
      log.info("post2 = {}  , {}", updateForm.getTitle(),  updateForm.getDetail());
      model.addAttribute("postData", updateForm);
      model.addAttribute("s_err_detail","1글자 이상 작성하세요");
      return "post/updateForm";
    }

        //정상처리
        Post sendpost = new Post();
        sendpost.setTitle(updateForm.getTitle());
        sendpost.setEmail(updateForm.getEmail());
        sendpost.setDetail(updateForm.getDetail());
        int updateRowCnt = postSVC.updateById(postId, sendpost);

        redirectAttributes.addAttribute("pid", postId);
        return "redirect:/post/{pid}/detail";

  }


}
