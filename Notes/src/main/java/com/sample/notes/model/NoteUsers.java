package com.sample.notes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="note_users")
public class NoteUsers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	
	private String user_name;
	
	private String password;
	
	private String email_id;
	
	private Date created_at;
	
	private Date updated_at;
	
	public NoteUsers() {
		
	}

	public NoteUsers(Long id, String user_name, String password, String email_id, Date created_at, Date updated_at) {
		super();
		this.user_id = id;
		this.user_name = user_name;
		this.password = password;
		this.email_id = email_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Long getUser_Id() {
		return user_id;
	}

	public void setUser_Id(Long id) {
		this.user_id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
}
