package com.cuet.today.service;

import com.cuet.today.dao.PostDAO;
import com.cuet.today.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    // Create new post
    public int createPost(Post post) {
        try {
            return postDAO.createPost(post);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Get all posts
    public List<Post> getAllPosts() {
        try {
            return postDAO.getAllPosts();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get posts by user ID
    public List<Post> getPostsByUserId(int userId) {
        try {
            return postDAO.getPostsByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get post by ID
    public Post getPostById(int postId) {
        try {
            return postDAO.getPostById(postId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update post
    public boolean updatePost(Post post, int userId) {
        try {
            // Check if user owns the post
            if (!postDAO.isPostOwner(post.getPostId(), userId)) {
                return false; // User doesn't own the post
            }
            return postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete post
    public boolean deletePost(int postId, int userId) {
        try {
            // Check if user owns the post
            if (!postDAO.isPostOwner(postId, userId)) {
                return false; // User doesn't own the post
            }
            return postDAO.deletePost(postId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if user owns the post
    public boolean isPostOwner(int postId, int userId) {
        return postDAO.isPostOwner(postId, userId);
    }
}