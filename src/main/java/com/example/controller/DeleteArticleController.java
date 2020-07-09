package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("ex-bbs")
public class DeleteArticleController {
	@Autowired
    private ArticleRepository articleRepository;
	/**
     * 投稿記事を削除し、投稿画面へ戻るメソッド
     *
     * @param id ID
     *
     * @return 投稿画面
     */
    @RequestMapping("/article-delete")
    public String articleDelete(String id) {
        articleRepository.deleteById(Integer.parseInt(id));
        return "redirect:/ex-bbs";
    }
}
