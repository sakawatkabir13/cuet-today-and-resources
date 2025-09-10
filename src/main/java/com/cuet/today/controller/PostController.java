package com.cuet.today.controller;

import com.cuet.today.model.Post;
import com.cuet.today.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Create new post
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Post post, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        post.setUserId(userId);
        int postId = postService.createPost(post);
        
        if (postId > 0) {
            response.put("success", true);
            response.put("message", "Post created successfully");
            response.put("postId", postId);
        } else {
            response.put("success", false);
            response.put("message", "Failed to create post");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get all posts
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        Map<String, Object> response = new HashMap<>();
        List<Post> posts = postService.getAllPosts();
        
        if (posts != null) {
            response.put("success", true);
            response.put("posts", posts);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch posts");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getPostsByUserId(@PathVariable int userId) {
        Map<String, Object> response = new HashMap<>();
        List<Post> posts = postService.getPostsByUserId(userId);
        
        if (posts != null) {
            response.put("success", true);
            response.put("posts", posts);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch posts");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get my posts
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyPosts(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        List<Post> posts = postService.getPostsByUserId(userId);
        
        if (posts != null) {
            response.put("success", true);
            response.put("posts", posts);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch posts");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable int postId) {
        Map<String, Object> response = new HashMap<>();
        Post post = postService.getPostById(postId);
        
        if (post != null) {
            response.put("success", true);
            response.put("post", post);
        } else {
            response.put("success", false);
            response.put("message", "Post not found");
        }
        
        return ResponseEntity.ok(response);
    }

    // Update post
    @PutMapping("/update/{postId}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable int postId, 
                                                         @RequestBody Post post, 
                                                         HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        post.setPostId(postId);
        boolean success = postService.updatePost(post, userId);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Post updated successfully");
        } else {
            response.put("success", false);
            response.put("message", "Failed to update post or you don't have permission");
        }
        
        return ResponseEntity.ok(response);
    }

    // Delete post
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable int postId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        boolean success = postService.deletePost(postId, userId);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Post deleted successfully");
        } else {
            response.put("success", false);
            response.put("message", "Failed to delete post or you don't have permission");
        }
        
        return ResponseEntity.ok(response);
    }
}