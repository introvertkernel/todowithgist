package com.project.todowithgist.payload;

public class UserPayloadWrapper {

	private ResponseHeader responseHeader;
	private UserPayload userPayload;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public UserPayload getUserPayload() {
		return userPayload;
	}

	public void setUserPayload(UserPayload userPayload) {
		this.userPayload = userPayload;
	}

	@Override
	public String toString() {
		return "UserPayloadWrapper [responseHeader=" + responseHeader + ", userPayload=" + userPayload + "]";
	}

}
