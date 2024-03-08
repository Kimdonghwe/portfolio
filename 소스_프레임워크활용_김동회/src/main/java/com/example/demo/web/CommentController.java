package com.example.demo.web;


import com.example.demo.domain.comment.svc.CommentSVC;
import com.example.demo.domain.entity.Comment;
import com.example.demo.web.form.comment.CommentAddForm;
import com.example.demo.web.form.post.AllData;
import com.example.demo.web.form.res.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
// Controller 역할을 하는 클래스
@AllArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
  private CommentSVC commentSVC;

  //댓글목록 불러오기
  @ResponseBody
  @GetMapping("/{pid}/comment")
  public ApiResponse<?> getComment(@PathVariable("pid") Long postId
          , @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
                                   @RequestParam(value = "reqCnt", defaultValue = "10") Long reqCnt ){

    // 선택한 상품아이디를 받아와서 상품에대한 모든 댓글 호출
    List<Comment> comments = commentSVC.findbyIdAll(postId,reqPage,reqCnt);

    log.info("comments = {}", comments);

    // 선택한 상품과 해당상품에대한 댓글을 객체로 감싸서 body로 만든다.
    AllData allData = new AllData(comments);

    // json 파일형식으로 바꾼다.
    ApiResponse<AllData> res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(),ResCode.OK.name(),"",allData);

    int commentByIdCnt = commentSVC.tototalbyIdCnt(postId);

    res.setTotalCnt(commentByIdCnt);

    return res;

  }

  // 댓글추가
  @ResponseBody
  @PostMapping("/{pid}/addComment")
  public String addComment(@PathVariable("pid") Long postId,
                           @RequestBody CommentAddForm commentAddForm
  ) {

    log.info("detail = {}", commentAddForm.getDetail());
    Long commentsId = commentSVC.addComment(postId,commentAddForm.getEmail(),commentAddForm.getDetail());


    return Long.toString(commentsId);
  }

  //댓글삭제
  @ResponseBody
  @DeleteMapping("/{cid}/del")
  public ApiResponse<?> delComment(@PathVariable("cid") Long commentsId) {

    log.info("commentsId = {}", commentsId);
    ApiResponse<String> res = null;
    if(commentSVC.delComment(commentsId)){

       res = ApiResponse.createApiResponse(ResCode.OK.getCode(),ResCode.OK.name(),null);

    }
    else{
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(),ResCode.FAIL.name(),null);

    }

    log.info("OKOK = {}", res);
    return res;
  }


  //댓글수정
  @ResponseBody
  @PatchMapping("/{cid}/modify")
  public  ApiResponse<?> modifyComment(@PathVariable("cid") Long commentsId,
                                       @RequestBody CommentAddForm commentAddForm
                                       ){

    ApiResponse<String> res = null;

    if(commentSVC.modifyComment(commentsId,commentAddForm.getDetail()) ) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(),ResCode.OK.name(),"");
    }
    else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(),ResCode.FAIL.name(),"");
    }

    return res;

  }
}
