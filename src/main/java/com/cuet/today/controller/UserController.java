package com.cuet.today.controller;

import com.cuet.today.model.User;
import com.cuet.today.model.Student;
import com.cuet.today.model.Faculty;
import com.cuet.today.model.Alumni;
import com.cuet.today.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register student
    @PostMapping("/register/student")
    public ResponseEntity<Map<String, Object>> registerStudent(@RequestBody Student student) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.registerStudent(student);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Student registered successfully");
        } else {
            response.put("success", false);
            response.put("message", "Registration failed. Email might already exist.");
        }
        
        return ResponseEntity.ok(response);
    }

    // Register faculty
    @PostMapping("/register/faculty")
    public ResponseEntity<Map<String, Object>> registerFaculty(@RequestBody Faculty faculty) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.registerFaculty(faculty);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Faculty registered successfully");
        } else {
            response.put("success", false);
            response.put("message", "Registration failed. Email might already exist.");
        }
        
        return ResponseEntity.ok(response);
    }

    // Register alumni
    @PostMapping("/register/alumni")
    public ResponseEntity<Map<String, Object>> registerAlumni(@RequestBody Alumni alumni) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.registerAlumni(alumni);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Alumni registered successfully");
        } else {
            response.put("success", false);
            response.put("message", "Registration failed. Email might already exist.");
        }
        
        return ResponseEntity.ok(response);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String email = loginData.get("email");
        String password = loginData.get("password");
        
        User user = userService.loginUser(email, password);
        
        if (user != null) {
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userType", user.getUserType().name());
            
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "Invalid email or password");
        }
        
        return ResponseEntity.ok(response);
    }

    // Logout user
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        session.invalidate();
        response.put("success", true);
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }

    // Get current user
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId != null) {
            Object userDetails = userService.getDetailedUserInfo(userId);
            response.put("success", true);
            response.put("user", userDetails);
        } else {
            response.put("success", false);
            response.put("message", "User not logged in");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<User> users = userService.getAllUsers();
        response.put("success", true);
        response.put("users", users);
        return ResponseEntity.ok(response);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable int userId) {
        Map<String, Object> response = new HashMap<>();
        Object userDetails = userService.getDetailedUserInfo(userId);
        
        if (userDetails != null) {
            response.put("success", true);
            response.put("user", userDetails);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        
        return ResponseEntity.ok(response);
    }
}