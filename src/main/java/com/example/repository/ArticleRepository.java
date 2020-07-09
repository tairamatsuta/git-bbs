package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * 投稿記事を操作するリポジトリです.
 * 
 * @author kazuteru.takahashi
 *
 */
@Repository
public class ArticleRepository {
	private static final String ARTICLES_TABLE_NAME = "articles";
	private static final String COMMENTS_TABLE_NAME = "comments";
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 投稿記事をすべて検索するメソッ
	 * 
	 * @return すべての投稿記事(0件ならばnull)
	 */
	public List<Article> findAll() {
		String sql = "SELECT id,name,content FROM " + ARTICLES_TABLE_NAME + " ORDER BY id DESC;";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}

	/**
	 * 記事情報とコメント情報を連結した結果をすべて検索するメソッド.
	 * 
	 * @return 記事情報とコメント情報の検索結果(コメントがない場合はコメント情報はnull)
	 */
	public List<Article> findAllWithComment() {
		String sql = "SELECT a.id article_id,a.name article_name,a.content article_content,"
				+ "b.id comment_id,b.name comment_name,b.content comment_content,b.article_id comment_article_id "
				+ "FROM " + ARTICLES_TABLE_NAME + " a FULL JOIN " + COMMENTS_TABLE_NAME + " b on a.id = b.article_id "
				+ "ORDER BY a.id DESC,b.id DESC;";
		return template.query(sql, new ResultSetExtractor<List<Article>>() {
			@Override
			public List<Article> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Article> articleList = new ArrayList<>();
				List<Comment> commentList = null;
				Article article = null;

				int beforeArticleId = -1;
				while (rs.next()) {
					if (rs.getInt("article_id") != beforeArticleId) {
						article = new Article();
						article.setId(rs.getInt("article_id"));
						article.setName(rs.getString("article_name"));
						article.setContent(rs.getString("article_content"));
						//先に空のcommentListを生成しarticleにセットする
						commentList = new ArrayList<>();
						article.setCommentList(commentList);
						
						articleList.add(article);
					}

					if (article.getId() == rs.getInt("comment_article_id")) {
						Comment comment = new Comment();
						comment.setId(rs.getInt("comment_id"));
						comment.setName(rs.getString("comment_name"));
						comment.setContent(rs.getString("comment_content"));
						comment.setArticleId(rs.getInt("comment_article_id"));
						//articleListのarticleのcommentListを参照して挿入している
						commentList.add(comment);
					}
					beforeArticleId = rs.getInt("article_id");
				}
				return articleList;
			}
		});
	}

	/**
	 * 投稿記事をDBへ挿入するメソッド.
	 * 
	 * @param article 投稿記事
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO " + ARTICLES_TABLE_NAME + "(name,content) values(:name,:content);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
				article.getContent());
		template.update(sql, param);
	}

	/**
	 * 指定されたIDの投稿を削除するメソッド.
	 * 
	 * @param id ID
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM " + ARTICLES_TABLE_NAME + " WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
