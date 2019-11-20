package com.seer.dto;

import java.sql.Timestamp;

public class UserProfile {
    public Long id;
    public Long customerId;
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public Boolean enabled;
    public Timestamp createdTime;
    public Timestamp lastUpdateTime;
    public Timestamp lastLoginTime;
    public String plainPassword;
    public int role;
}
