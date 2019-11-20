package com.seer.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public int errorCode = 1;

	public DAOException() {
		super();
	}

	public DAOException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}	

	public DAOException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public DAOException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
}
