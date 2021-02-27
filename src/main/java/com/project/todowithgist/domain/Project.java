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
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private User user;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "PROJECT_DESC")
	private String projectDescription;

	@Column(name = "CREATE_TS")
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

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Project(String projectId, User user, String projectName, String projectDescription) {
		super();
		this.projectId = projectId;
		this.user = user;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
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
		return "Project [projectId=" + projectId + ", user=" + user + ", projectName=" + projectName
				+ ", projectDescription=" + projectDescription + "]";
	}

}
