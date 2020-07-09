package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("ex-bbs")
public class ShowBbsController {
	@Autowired
    private ArticleRepository articleRepository;
    /**
     * 投稿画面を表示するメソッド.
     *
     * @return 投稿画面
     */
    @RequestMapping("")
    public String index(Model model) {
        List<Article> articles = articleRepository.findAllWithComment();
        if (articles.size() == 0) {
            model.addAttribute("articleNone", 1);
        }
        model.addAttribute("articles", articles);
        return "bbs-home";
    }
}
