package com.cuet.today.controller;

import com.cuet.today.model.Resource;
import com.cuet.today.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    // Create new resource
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createResource(@RequestBody Resource resource, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        resource.setUserId(userId);
        int resourceId = resourceService.createResource(resource);
        
        if (resourceId > 0) {
            response.put("success", true);
            response.put("message", "Resource created successfully");
            response.put("resourceId", resourceId);
        } else {
            response.put("success", false);
            response.put("message", "Failed to create resource");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get all resources
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllResources() {
        Map<String, Object> response = new HashMap<>();
        List<Resource> resources = resourceService.getAllResources();
        
        if (resources != null) {
            response.put("success", true);
            response.put("resources", resources);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch resources");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get resources by category
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getResourcesByCategory(@PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Resource.ResourceCategory resourceCategory = Resource.ResourceCategory.valueOf(category.toUpperCase());
            List<Resource> resources = resourceService.getResourcesByCategory(resourceCategory);
            
            if (resources != null) {
                response.put("success", true);
                response.put("resources", resources);
            } else {
                response.put("success", false);
                response.put("message", "Failed to fetch resources");
            }
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Invalid category");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get resources by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getResourcesByUserId(@PathVariable int userId) {
        Map<String, Object> response = new HashMap<>();
        List<Resource> resources = resourceService.getResourcesByUserId(userId);
        
        if (resources != null) {
            response.put("success", true);
            response.put("resources", resources);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch resources");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get my resources
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyResources(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        List<Resource> resources = resourceService.getResourcesByUserId(userId);
        
        if (resources != null) {
            response.put("success", true);
            response.put("resources", resources);
        } else {
            response.put("success", false);
            response.put("message", "Failed to fetch resources");
        }
        
        return ResponseEntity.ok(response);
    }

    // Get resource by ID
    @GetMapping("/{resourceId}")
    public ResponseEntity<Map<String, Object>> getResourceById(@PathVariable int resourceId) {
        Map<String, Object> response = new HashMap<>();
        Resource resource = resourceService.getResourceById(resourceId);
        
        if (resource != null) {
            response.put("success", true);
            response.put("resource", resource);
        } else {
            response.put("success", false);
            response.put("message", "Resource not found");
        }
        
        return ResponseEntity.ok(response);
    }

    // Update resource
    @PutMapping("/update/{resourceId}")
    public ResponseEntity<Map<String, Object>> updateResource(@PathVariable int resourceId, 
                                                             @RequestBody Resource resource, 
                                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        resource.setResourceId(resourceId);
        boolean success = resourceService.updateResource(resource, userId);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Resource updated successfully");
        } else {
            response.put("success", false);
            response.put("message", "Failed to update resource or you don't have permission");
        }
        
        return ResponseEntity.ok(response);
    }

    // Delete resource
    @DeleteMapping("/delete/{resourceId}")
    public ResponseEntity<Map<String, Object>> deleteResource(@PathVariable int resourceId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.ok(response);
        }
        
        boolean success = resourceService.deleteResource(resourceId, userId);
        
        if (success) {
            response.put("success", true);
            response.put("message", "Resource deleted successfully");
        } else {
            response.put("success", false);
            response.put("message", "Failed to delete resource or you don't have permission");
        }
        
        return ResponseEntity.ok(response);
    }
}