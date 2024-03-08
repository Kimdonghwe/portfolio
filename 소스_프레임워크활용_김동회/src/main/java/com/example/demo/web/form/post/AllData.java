package com.example.demo.web.form.post;

import com.example.demo.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AllData {
  List<Comment> comments;
}
