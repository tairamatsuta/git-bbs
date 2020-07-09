package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

/**
 * 記事情報を登録するコントローラ.
 * 
 * @author taira.matsuta
 *
 */
@Controller
@RequestMapping("/ex-bbs")
public class InsertArticleController {

	@Autowired
	private ShowBbsController showBbsController;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * 記事情報を登録する.
	 * 
	 * @param articleForm フォーム
	 * @param result　エラー
	 * @param model　
	 * @return
	 */
	@RequestMapping("/article-post")
	public String insertArticle(
			@Validated ArticleForm articleForm
			, BindingResult result
			, Model model) {
		if(result.hasErrors()) {
			return showBbsController.index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		return "redirect:/bbs/";
	}
}
