package com.cuet.today.dao;

import com.cuet.today.model.Resource;
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
public class ResourceDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for Resource
    private static class ResourceRowMapper implements RowMapper<Resource> {
        @Override
        public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
            Resource resource = new Resource();
            resource.setResourceId(rs.getInt("resource_id"));
            resource.setUserId(rs.getInt("user_id"));
            resource.setTitle(rs.getString("title"));
            resource.setDescription(rs.getString("description"));
            resource.setUrl(rs.getString("url"));
            resource.setCategory(Resource.ResourceCategory.valueOf(rs.getString("category")));
            resource.setCreatedAt(rs.getString("created_at"));
            // Fetch user name and type
            resource.setUserName(rs.getString("user_name"));
            resource.setUserType(rs.getString("user_type"));
            return resource;
        }
    }

    // Create resource
    public int createResource(Resource resource) {
        String sql = "INSERT INTO resources (user_id, title, description, url, category) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"resource_id"});
            ps.setInt(1, resource.getUserId());
            ps.setString(2, resource.getTitle());
            ps.setString(3, resource.getDescription());
            ps.setString(4, resource.getUrl());
            ps.setString(5, resource.getCategory().name());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
    }

    // Get all resources
    public List<Resource> getAllResources() {
        String sql = "SELECT r.*, u.name AS user_name, u.user_type FROM resources r JOIN users u ON r.user_id = u.user_id ORDER BY r.created_at DESC";
        return jdbcTemplate.query(sql, new ResourceRowMapper());
    }

    // Get resources by category
    public List<Resource> getResourcesByCategory(Resource.ResourceCategory category) {
        String sql = "SELECT r.*, u.name AS user_name, u.user_type FROM resources r JOIN users u ON r.user_id = u.user_id WHERE r.category = ? ORDER BY r.created_at DESC";
        return jdbcTemplate.query(sql, new ResourceRowMapper(), category.name());
    }

    // Get resources by user ID
    public List<Resource> getResourcesByUserId(int userId) {
        String sql = "SELECT r.*, u.name AS user_name, u.user_type FROM resources r JOIN users u ON r.user_id = u.user_id WHERE r.user_id = ? ORDER BY r.created_at DESC";
        return jdbcTemplate.query(sql, new ResourceRowMapper(), userId);
    }

    // Get resource by ID
    public Resource getResourceById(int resourceId) {
        String sql = "SELECT r.*, u.name AS user_name, u.user_type FROM resources r JOIN users u ON r.user_id = u.user_id WHERE r.resource_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ResourceRowMapper(), resourceId);
        } catch (Exception e) {
            return null;
        }
    }

    // Update resource
    public boolean updateResource(Resource resource) {
        String sql = "UPDATE resources SET title = ?, description = ?, url = ?, category = ? WHERE resource_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, 
            resource.getTitle(), 
            resource.getDescription(), 
            resource.getUrl(), 
            resource.getCategory().name(), 
            resource.getResourceId());
        return rowsAffected > 0;
    }

    // Delete resource
    public boolean deleteResource(int resourceId) {
        String sql = "DELETE FROM resources WHERE resource_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, resourceId);
        return rowsAffected > 0;
    }

    // Check if user owns the resource
    public boolean isResourceOwner(int resourceId, int userId) {
        String sql = "SELECT COUNT(*) FROM resources WHERE resource_id = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, resourceId, userId);
        return count != null && count > 0;
    }
}