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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_TODO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String todoId;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;

	@Column(name = "TODO_DESC")
	private String todoDesc;

	@Column(name = "TODO_STATUS")
	private String todoStatus;
	
	@Column(name = "UPDATE_TS")
	private LocalDateTime updateTs;

	@Column(name = "CREATE_TS")
	private LocalDateTime createTs;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTodoDesc() {
		return todoDesc;
	}

	public void setTodoDesc(String todoDesc) {
		this.todoDesc = todoDesc;
	}

	public String getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}

	public Todo(String todoId, Project project, String todoDesc, String todoStatus) {
		super();
		this.todoId = todoId;
		this.project = project;
		this.todoDesc = todoDesc;
		this.todoStatus = todoStatus;
	}

	public Todo() {
		super();
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", project=" + project + ", todoDesc=" + todoDesc + ", todoStatus="
				+ todoStatus + "]";
	}

}
