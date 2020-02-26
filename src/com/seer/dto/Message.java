package com.seer.dto;

import java.sql.Date;

public class Message {
    public Long id;
    public Long buildingId;
    public Long apartmentId;
    public String apartmentNumber;
    public String apartmentLastName;
    public String title;
    public String body;
    public Date dateCreated;
    public Boolean read;
    public Boolean sentBySeer;

}
