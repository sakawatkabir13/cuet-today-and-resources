package com.cuet.today.service;

import com.cuet.today.dao.ResourceDAO;
import com.cuet.today.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceDAO resourceDAO;

    // Create new resource
    public int createResource(Resource resource) {
        try {
            return resourceDAO.createResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Get all resources
    public List<Resource> getAllResources() {
        try {
            return resourceDAO.getAllResources();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get resources by category
    public List<Resource> getResourcesByCategory(Resource.ResourceCategory category) {
        try {
            return resourceDAO.getResourcesByCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get resources by user ID
    public List<Resource> getResourcesByUserId(int userId) {
        try {
            return resourceDAO.getResourcesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get resource by ID
    public Resource getResourceById(int resourceId) {
        try {
            return resourceDAO.getResourceById(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update resource
    public boolean updateResource(Resource resource, int userId) {
        try {
            // Check if user owns the resource
            if (!resourceDAO.isResourceOwner(resource.getResourceId(), userId)) {
                return false; // User doesn't own the resource
            }
            return resourceDAO.updateResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete resource
    public boolean deleteResource(int resourceId, int userId) {
        try {
            // Check if user owns the resource
            if (!resourceDAO.isResourceOwner(resourceId, userId)) {
                return false; // User doesn't own the resource
            }
            return resourceDAO.deleteResource(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if user owns the resource
    public boolean isResourceOwner(int resourceId, int userId) {
        return resourceDAO.isResourceOwner(resourceId, userId);
    }
}