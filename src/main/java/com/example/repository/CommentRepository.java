package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * 
 * @author taira.matsuta
 *
 */
@Repository
public class CommentRepository {

	/**
	 * テーブル名
	 */
	private static final String TABLE_NAME = "comments";

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Commentオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);
	
	/**
	 * コメント情報を検索する.(artile_idで検索)
	 * 
	 * @param articleId　記事ID
	 * @return　コメント情報
	 */
	public List<Comment>findByArticleId(int articleId) {
		String sql = "SELECT id, name, content, article_id FROM " + TABLE_NAME + " WHERE article_id = :articleId;";
		SqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
		return template.query(sql, param, COMMENT_ROW_MAPPER);
	}
	
	/**
	 * コメント情報を挿入する.
	 * 
	 * @param comment コメント情報
	 */
	public void insert(Comment comment) {
		String insertSql = "INSERT INTO " + TABLE_NAME + "(name, content, article_id) VALUES(:name, :content, :articleId);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());
		template.update(insertSql, param);
	}
	
	/**
	 * コメント情報を削除する.
	 * 
	 * @param articleId　記事ID
	 */
	public void deleteByArticleId(int articleId) {
		String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE article_id = :articleId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(deleteSql, param);
	}
	
}
