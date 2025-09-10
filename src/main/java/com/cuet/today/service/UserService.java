package com.cuet.today.service;

import com.cuet.today.dao.UserDAO;
import com.cuet.today.model.User;
import com.cuet.today.model.Student;
import com.cuet.today.model.Faculty;
import com.cuet.today.model.Alumni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // Register student
    public boolean registerStudent(Student student) {
        try {
            // Check if email already exists
            if (userDAO.findUserByEmail(student.getEmail()) != null) {
                return false; // Email already exists
            }
            // Hash password
            student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
            userDAO.createStudent(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Register faculty
    public boolean registerFaculty(Faculty faculty) {
        try {
            // Check if email already exists
            if (userDAO.findUserByEmail(faculty.getEmail()) != null) {
                return false; // Email already exists
            }
            // Hash password
            faculty.setPassword(BCrypt.hashpw(faculty.getPassword(), BCrypt.gensalt()));
            userDAO.createFaculty(faculty);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Register alumni
    public boolean registerAlumni(Alumni alumni) {
        try {
            // Check if email already exists
            if (userDAO.findUserByEmail(alumni.getEmail()) != null) {
                return false; // Email already exists
            }
            // Hash password
            alumni.setPassword(BCrypt.hashpw(alumni.getPassword(), BCrypt.gensalt()));
            userDAO.createAlumni(alumni);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login user
    public User loginUser(String email, String password) {
        try {
            User user = userDAO.findUserByEmail(email);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get user by ID
    public User getUserById(int userId) {
        return userDAO.findUserById(userId);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Update user
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    // Delete user
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    // Get detailed user information based on type
    public Object getDetailedUserInfo(int userId) {
        User user = userDAO.findUserById(userId);
        if (user == null) {
            return null;
        }

        switch (user.getUserType()) {
            case STUDENT:
                return userDAO.getStudentDetails(userId);
            case FACULTY:
                return userDAO.getFacultyDetails(userId);
            case ALUMNI:
                return userDAO.getAlumniDetails(userId);
            default:
                return user;
        }
    }
}