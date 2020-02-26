package com.seer.dto;

import java.sql.Date;

public class Task {
    public Long id;
    public Long buildingId;
    public Long apartmentId;
    public Long taskCategoryId;
    public String name;
    public String description;
    public Boolean completed;
    public Date dueDate;
}
