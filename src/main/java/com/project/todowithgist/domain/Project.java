package com.project.todowithgist.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
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

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TS", updatable = false)
	private Date createTs;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "project")
	private List<Todo> todoList;

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

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public Project(String projectId, User user, String projectName, String gistID) {
		super();
		this.projectId = projectId;
		this.user = user;
		this.projectName = projectName;
		this.gistID = gistID;
	}

	public Project(String projectId, User user, String projectName, String gistID, Date createTs) {
		this(projectId, user, projectName, gistID);
		this.createTs = createTs;
	}

	public Project(String projectId) {
		super();
		this.projectId = projectId;
	}

	public Project() {
		super();
	}

//	@Override
//	public String toString() {
//		return "Project [projectId=" + projectId + ", user=" + user + ", projectName=" + projectName + ", gistID="
//				+ gistID + ", createTs=" + createTs + "]";
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTs == null) ? 0 : createTs.hashCode());
		result = prime * result + ((gistID == null) ? 0 : gistID.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (createTs == null) {
			if (other.createTs != null)
				return false;
		} else if (!createTs.equals(other.createTs))
			return false;
		if (gistID == null) {
			if (other.gistID != null)
				return false;
		} else if (!gistID.equals(other.gistID))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
