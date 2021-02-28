package com.project.todowithgist.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "TB_PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String projectId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "GIST_ID")
	private String gistID;

	@Column(name = "CREATE_TS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private LocalDateTime createTs;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getGistID() {
		return gistID;
	}

	public void setGistID(String gistID) {
		this.gistID = gistID;
	}

	public Project(String projectId, User user, String projectName, String gistID, LocalDateTime createTs) {
		super();
		this.projectId = projectId;
		this.user = user;
		this.projectName = projectName;
		this.gistID = gistID;
		this.createTs = createTs;
	}

	public Project(String projectId) {
		super();
		this.projectId = projectId;
	}

	public Project() {
		super();
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", user=" + user + ", projectName=" + projectName + ", gistID="
				+ gistID + ", createTs=" + createTs + "]";
	}

}
