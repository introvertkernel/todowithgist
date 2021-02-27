package com.project.todowithgist.payload;

public class GistResponse {

	private String id;
	private String created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "GistResponse [id=" + id + ", created_at=" + created_at + "]";
	}

}
