package com.project.todowithgist.payload;

public class ResponseHeader {

	private String responseCode;
	private String errorCode;
	private String errorMessage;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	protected ResponseHeader(String responseCode, String errorCode, String errorMessage) {
		super();
		this.responseCode = responseCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ResponseHeader [responseCode=" + responseCode + ", errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + "]";
	}

	public ResponseHeader() {
		super();
	}

}
