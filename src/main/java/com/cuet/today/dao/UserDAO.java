package com.cuet.today.dao;

import com.cuet.today.model.User;
import com.cuet.today.model.Student;
import com.cuet.today.model.Faculty;
import com.cuet.today.model.Alumni;
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
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for User
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
            user.setCreatedAt(rs.getString("created_at"));
            return user;
        }
    }

    // Create user and return generated ID
    public int createUser(User user) {
        String sql = "INSERT INTO users (name, email, password, user_type) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"user_id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserType().name());
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
    }

    // Create student
    public void createStudent(Student student) {
        int userId = createUser(student);
        String sql = "INSERT INTO students (user_id, batch) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, student.getBatch());
    }

    // Create faculty
    public void createFaculty(Faculty faculty) {
        int userId = createUser(faculty);
        String sql = "INSERT INTO faculty (user_id, research_area) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, faculty.getResearchArea());
    }

    // Create alumni
    public void createAlumni(Alumni alumni) {
        int userId = createUser(alumni);
        String sql = "INSERT INTO alumni (user_id, research_area, current_workplace, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, alumni.getResearchArea(), alumni.getCurrentWorkplace(), alumni.getDescription());
    }

    // Find user by ID
    public User findUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
        } catch (Exception e) {
            return null;
        }
    }

    // Find user by email
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
        } catch (Exception e) {
            return null;
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // Update user
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getUserId());
        return rowsAffected > 0;
    }

    // Delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    // Get student details
    public Student getStudentDetails(int userId) {
        String sql = "SELECT u.*, s.batch FROM users u JOIN students s ON u.user_id = s.user_id WHERE u.user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Student student = new Student();
                student.setUserId(rs.getInt("user_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                student.setCreatedAt(rs.getString("created_at"));
                student.setBatch(rs.getString("batch"));
                return student;
            }, userId);
        } catch (Exception e) {
            return null;
        }
    }

    // Get faculty details
    public Faculty getFacultyDetails(int userId) {
        String sql = "SELECT u.*, f.research_area FROM users u JOIN faculty f ON u.user_id = f.user_id WHERE u.user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Faculty faculty = new Faculty();
                faculty.setUserId(rs.getInt("user_id"));
                faculty.setName(rs.getString("name"));
                faculty.setEmail(rs.getString("email"));
                faculty.setPassword(rs.getString("password"));
                faculty.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                faculty.setCreatedAt(rs.getString("created_at"));
                faculty.setResearchArea(rs.getString("research_area"));
                return faculty;
            }, userId);
        } catch (Exception e) {
            return null;
        }
    }

    // Get alumni details
    public Alumni getAlumniDetails(int userId) {
        String sql = "SELECT u.*, a.research_area, a.current_workplace, a.description FROM users u JOIN alumni a ON u.user_id = a.user_id WHERE u.user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Alumni alumni = new Alumni();
                alumni.setUserId(rs.getInt("user_id"));
                alumni.setName(rs.getString("name"));
                alumni.setEmail(rs.getString("email"));
                alumni.setPassword(rs.getString("password"));
                alumni.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                alumni.setCreatedAt(rs.getString("created_at"));
                alumni.setResearchArea(rs.getString("research_area"));
                alumni.setCurrentWorkplace(rs.getString("current_workplace"));
                alumni.setDescription(rs.getString("description"));
                return alumni;
            }, userId);
        } catch (Exception e) {
            return null;
        }
    }
}