package com.cuet.today.model;

public class Resource {
    private int resourceId;
    private int userId;
    private String title;
    private String description;
    private String url;
    private ResourceCategory category;
    private String createdAt;
    private String userName;
    private String userType;

    public enum ResourceCategory {
        ACADEMIC, HIGHER_STUDY, OTHERS
    }

    // Default constructor
    public Resource() {}

    // Constructor with parameters
    public Resource(int userId, String title, String description, String url, ResourceCategory category) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.category = category;
    }

    // Getters and Setters
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public void setCategory(ResourceCategory category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}