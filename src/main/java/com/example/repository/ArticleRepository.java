package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * articlesテーブルを操作するリポジトリ.
 * 
 * @author taira.matsuta
 *
 */
@Repository
public class ArticleRepository {

	/**
	 * テーブル名
	 */
	private static final String TABLE_NAME = "articles";
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Articleオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
	
	/**
	 * 記事情報を全件検索する.
	 * 
	 * @return　記事情報
	 */
	public List<Article> findAll() {
		String sql = "SELECT id, name, content FROM " + TABLE_NAME + " ORDER BY id DESC;";
		//String sql = "SELECT a.id, a.name, a.content, c.id, c.name, c.content, c.article_id FROM articles a JOIN comments c ON a.id = c.article_id;";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
	/**
	 * 記事情報を挿入する.
	 * 
	 * @param article
	 */
	public void insert(Article article) {
		String insertSql = "INSERT INTO " + TABLE_NAME + "(name, content) VALUES(:name, :content);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		template.update(insertSql, param);
	}
	
	/**
	 * 記事情報を削除する.
	 * 
	 * @param id 記事ID
	 */
	public void deleteById(int id) {
		String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(deleteSql, param);
	}
	
}
