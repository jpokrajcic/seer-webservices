package com.seer.services.rest;

import com.seer.common.StatusCodes;
import com.seer.services.flex.ResponseData;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement
@XmlSeeAlso({/*UserProfile.class*/})
public class RESTResponseData
{
	public Integer status = Integer.valueOf(StatusCodes.OK);
	public String description = null;

	public Object data = null;

	public String responseText = null;

	public Float responseFloat = Float.NaN;
	
	public Integer responseInt = Integer.valueOf(Integer.MIN_VALUE);
	
	public RESTResponseData()
	{
	}

	public RESTResponseData(Object data)
	{
		if(data!=null){
			if(data instanceof String){
				this.responseText = data.toString();
			}
			else if(data instanceof Integer){
				this.responseInt = (Integer) data;
			}
			else if(data instanceof Float){
				this.responseFloat = (Float) data;
			}
			else{
				this.data = data;
			}
		}
	}

	public RESTResponseData(Integer status, String description, Object data) {
		this.status = status;
		this.description = description;
		if(data!=null){
			if(data instanceof String){
				this.responseText = (String) data;
			}
			else if(data instanceof Integer){
				this.responseInt = (Integer) data;
			}
			else if(data instanceof Float){
				this.responseFloat = (Float) data;
			}
			else{
				this.data = data;
			}
		}
	}

	public RESTResponseData(ResponseData rd) {
		this.status = rd.status;
		this.description = rd.description;

		if(rd.data!=null){
			this.data = rd.data;
//			if(rd.data instanceof String){
//				this.responseText = rd.data.toString();
//			}
//			else if(rd.data instanceof Integer){
//				this.responseInt = (Integer) rd.data;
//			}
//			else if(rd.data instanceof Float){
//				this.responseFloat = (Float) rd.data;
//			}
//			else{
//				this.data = rd.data;
//			}
		}
	}

}