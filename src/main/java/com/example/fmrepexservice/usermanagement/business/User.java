package com.example.fmrepexservice.usermanagement.business;

import com.example.fmrepexservice.security.Group;

import java.util.HashSet;
import java.util.Set;

public abstract class User {
    private String email;
    private String phoneNumber;
    private String fullName;
    private String password;
    private UserType userType;
    private boolean approved = false;
    private boolean verified = false;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", approved=" + approved +
                ", verified=" + verified +
                '}';
    }

    /**
     * Security section
     */
    private Set<Group> userGroups= new HashSet<>();
    private Boolean locked = false;
    public Set<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<Group> userGroups) {
        this.userGroups = userGroups;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public void addUserGroups(Group group) {
        userGroups.add(group);
    }

    public void removeUserGroups(Group group) {
        userGroups.remove(group);
    }
}
