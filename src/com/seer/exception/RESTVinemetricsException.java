package com.seer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RESTSeerException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public RESTSeerException(int errorCode) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(String.valueOf(errorCode)).build());
	}

	public RESTSeerException(String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("#" + message).build());
	}

	public RESTSeerException(int errorCode, String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(String.valueOf(errorCode) + "#" + message).build());
	}

}
