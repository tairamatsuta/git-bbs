package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("ex-bbs")
public class ShowBbsController {
	@Autowired
    private ArticleRepository articleRepository;
	@Autowired
    private HttpSession application;
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
    
    @ResponseBody
    @RequestMapping("/goodAdd")
    public Map<String, Integer> goodAdd(String id) {
        Map<String, Integer> map = new HashMap<>();
        Integer goodCount = (Integer) application.getAttribute("goodCounter" + id);
        if (goodCount == null) {
            goodCount = 1;
        } else {
            goodCount++;
        }
        application.setAttribute("goodCounter" + id, goodCount);
        map.put("goodCounter",goodCount);
        return map;
    }
}
