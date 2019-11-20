package com.seer.exception;

public class SeerException extends Exception {

	private static final long serialVersionUID = 1L;

	public int errorCode = 1;

	public SeerException() {
		super();
	}

	public SeerException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public SeerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeerException(Throwable cause) {
		super(cause);
	}

	public SeerException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public SeerException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
}
