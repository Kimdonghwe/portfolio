package com.example.demo.web;

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

  //목록
  @GetMapping   // GET http://localhost:9080/post
  public String findAll(Model model) {

    List<Post> list = postSVC.findAll();
    model.addAttribute("list", list);

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

      log.info("post1 = {} , {} , {}", addForm.getTitle(), addForm.getPname(), addForm.getDetail());
      model.addAttribute("s_err_title","제목은 15자 이내여야 합니다 공백이 포함되서는 안됩니다.");
      return "post/add";
    }
    String pattern_Pname = "^[a-zA-Z0-9ㄱ-ㅎ가-힣_\\-\s]{1,10}$";
    if(!Pattern.matches(pattern_Pname,addForm.getPname())){
      log.info("post2 = {} , {} , {}", addForm.getTitle(), addForm.getPname(), addForm.getDetail());
      model.addAttribute("s_err_pname","한영/10자이내");
      return "post/add";
    }
    String pattern_Detail = "^[ㄱ-ㅎ가-힣a-zA-Z0-9`~!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?\s]{1,}$";
    if(!Pattern.matches(pattern_Detail,addForm.getDetail())){
      log.info("post3 = {} , {} , {}", addForm.getTitle(), addForm.getPname(), addForm.getDetail());
      model.addAttribute("s_err_detail","1글자 이상 작성하세요");
      return "post/add";
    }
    Post post = new Post();
    post.setTitle(addForm.getTitle());
    post.setPname(addForm.getPname());
    post.setDetail(addForm.getDetail());

    Long postId = postSVC.save(post);
    redirectAttributes.addAttribute("pid", postId);
    return "redirect:/post/{pid}/detail";
  }

  //조회양식
  @GetMapping("/{pid}/detail")
  public String findById(@PathVariable("pid") Long postId,
                         Model model) {
    Optional<Post> findedpost = postSVC.findById(postId);
    Post post = findedpost.orElse(null);
    post.setPostId(postId);
    model.addAttribute("postData", post);

    return "post/detailForm";
  }

  //단건삭제
  @GetMapping("/{pid}/del")
  public String deleteById(@PathVariable("pid") Long postId){


//    1)상품 삭제 -> 상품테이블에서 삭제
    int deletedRowCnt = postSVC.deleteById(postId);

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
      log.info("post1 = {} , {} , {}", updateForm.getTitle(), updateForm.getPname(), updateForm.getDetail());
      model.addAttribute("postData", updateForm);
      model.addAttribute("s_err_title","제목은 15자 이내여야 합니다.");
      return "post/updateForm";
    }
    String pattern_Detail = "^[ㄱ-ㅎ가-힣a-zA-Z0-9`~!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?\s]{1,}$";
    if(!Pattern.matches(pattern_Detail,updateForm.getDetail())){
      log.info("post2 = {} , {} , {}", updateForm.getTitle(), updateForm.getPname(), updateForm.getDetail());
      model.addAttribute("postData", updateForm);
      model.addAttribute("s_err_detail","1글자 이상 작성하세요");
      return "post/updateForm";
    }

        //정상처리
        Post sendpost = new Post();
        sendpost.setTitle(updateForm.getTitle());
        sendpost.setPname(updateForm.getPname());
        sendpost.setDetail(updateForm.getDetail());
        int updateRowCnt = postSVC.updateById(postId, sendpost);

        redirectAttributes.addAttribute("pid", postId);
    return "redirect:/post/{pid}/detail";
  }
}
