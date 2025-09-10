package com.cuet.today.dao;

import com.cuet.today.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for Post
    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserId(rs.getInt("user_id"));
            post.setTitle(rs.getString("title"));
            post.setDescription(rs.getString("description"));
            post.setCreatedAt(rs.getString("created_at"));
            post.setUpdatedAt(rs.getString("updated_at"));
            // Fetch user name and type
            post.setUserName(rs.getString("user_name"));
            post.setUserType(rs.getString("user_type"));
            return post;
        }
    }

    // Create post
    public int createPost(Post post) {
        String sql = "INSERT INTO posts (user_id, title, description) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"post_id"});
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getDescription());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
    }

    // Get all posts
    public List<Post> getAllPosts() {
        String sql = "SELECT p.*, u.name AS user_name, u.user_type FROM posts p JOIN users u ON p.user_id = u.user_id ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    // Get posts by user ID
    public List<Post> getPostsByUserId(int userId) {
        String sql = "SELECT p.*, u.name AS user_name, u.user_type FROM posts p JOIN users u ON p.user_id = u.user_id WHERE p.user_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, new PostRowMapper(), userId);
    }

    // Get post by ID
    public Post getPostById(int postId) {
        String sql = "SELECT p.*, u.name AS user_name, u.user_type FROM posts p JOIN users u ON p.user_id = u.user_id WHERE p.post_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new PostRowMapper(), postId);
        } catch (Exception e) {
            return null;
        }
    }

    // Update post
    public boolean updatePost(Post post) {
        String sql = "UPDATE posts SET title = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE post_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, post.getTitle(), post.getDescription(), post.getPostId());
        return rowsAffected > 0;
    }

    // Delete post
    public boolean deletePost(int postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, postId);
        return rowsAffected > 0;
    }

    // Check if user owns the post
    public boolean isPostOwner(int postId, int userId) {
        String sql = "SELECT COUNT(*) FROM posts WHERE post_id = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, postId, userId);
        return count != null && count > 0;
    }
}