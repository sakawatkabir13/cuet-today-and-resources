package com.cuet.today.model;

public class Student extends User {
    private String batch;

    public Student() {
        super();
        this.setUserType(UserType.STUDENT);
    }

    public Student(String name, String email, String password, String batch) {
        super(name, email, password, UserType.STUDENT);
        this.batch = batch;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}