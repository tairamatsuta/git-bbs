package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Comment;
import com.example.form.CommentForm;

import com.example.repository.CommentRepository;

@Controller
@RequestMapping("ex-bbs")
public class InsertCommentController {
	@Autowired
	private ShowBbsController ShowBbsController;
	@Autowired
    private CommentRepository commentRepository;
	
	/**
     * コメントを投稿し投稿画面へ戻るメソッド.
     *
     * @param form   新規コメント
     * @param result バリデーションエラー情報
     *
     * @return 投稿画面
     */
    @RequestMapping("/comment-post")
    public String commentPost(@Validated CommentForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return ShowBbsController.index(model);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);
        comment.setArticleId(Integer.parseInt(form.getArticleId()));
        commentRepository.insert(comment);
        return "redirect:/ex-bbs";
    }
}
