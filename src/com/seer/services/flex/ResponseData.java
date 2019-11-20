package com.seer.services.flex;

public class ResponseData
{
  public Integer status;
  public String description;
  public Object data;

  public ResponseData()
  {
  }

  public ResponseData(Object data)
  {
    this.data = data;
  }
  
  public ResponseData(Integer status, String description, Object data) {
    this.status = status;
    this.description = description;
    this.data = data;
  }
}