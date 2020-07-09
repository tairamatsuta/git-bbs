package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメント情報を操作するリポジトリです.
 * 
 * @author kazuteru.takahashi
 *
 */
@Repository
public class CommentRepository {
	private static final String TABLE_NAME = "comments";
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 投稿記事IDから対応するコメントを検索するメソッド.
	 * 
	 * @param articleId 投稿記事ID
	 * @return 投稿記事IDに対応したコメント(0件ならばnull)
	 */
	public List<Comment> findByArticleId(int articleId){
		String sql = "SELECT id,name,content,article_id FROM "+TABLE_NAME+" "
				+ "WHERE article_id = :articleId ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		return template.query(sql, param, COMMENT_ROW_MAPPER);
	}
	
	/**
	 *投稿されたコメントを挿入するメソッド. 
	 * 
	 * @param comment 新規コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql= "INSERT INTO "+TABLE_NAME+"(name,content,article_id) VALUES(:name,:content,:articleId);";
		template.update(sql, param);
	}
	
	/**
	 * 指定された投稿記事IDのコメントをすべて削除するメソッド.
	 * 
	 * @param articleId 投稿記事ID
	 */
	public void deleteByArticleId(int articleId) {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE article_id = :articleId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
	
}
