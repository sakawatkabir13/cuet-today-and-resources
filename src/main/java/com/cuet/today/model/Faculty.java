package com.cuet.today.model;

public class Faculty extends User {
    private String researchArea;

    public Faculty() {
        super();
        this.setUserType(UserType.FACULTY);
    }

    public Faculty(String name, String email, String password, String researchArea) {
        super(name, email, password, UserType.FACULTY);
        this.researchArea = researchArea;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }
}