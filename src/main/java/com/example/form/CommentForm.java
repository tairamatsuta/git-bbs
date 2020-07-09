package com.example.form;


import javax.validation.constraints.NotBlank;

/**
 * コメント情報を保持するフォームです.
 * 
 * @author kazuteru.takahashi
 */
public class CommentForm {
	/** 投稿記事ID */
	private String articleId;
	/** コメント者名 */
	@NotBlank(message = "名前が未入力です")
	private String name;
	/** コメント内容 */
	@NotBlank(message = "コメントが未入力です")
	private String content;

	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
