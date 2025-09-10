package com.cuet.today.model;

public class Alumni extends User {
    private String researchArea;
    private String currentWorkplace;
    private String description;

    public Alumni() {
        super();
        this.setUserType(UserType.ALUMNI);
    }

    public Alumni(String name, String email, String password, String researchArea, 
                  String currentWorkplace, String description) {
        super(name, email, password, UserType.ALUMNI);
        this.researchArea = researchArea;
        this.currentWorkplace = currentWorkplace;
        this.description = description;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getCurrentWorkplace() {
        return currentWorkplace;
    }

    public void setCurrentWorkplace(String currentWorkplace) {
        this.currentWorkplace = currentWorkplace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}