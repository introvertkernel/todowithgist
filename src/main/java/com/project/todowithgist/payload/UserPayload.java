package com.project.todowithgist.payload;

public class UserPayload {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	protected UserPayload(String userName) {
		super();
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserPayload [userName=" + userName + "]";
	}

}
