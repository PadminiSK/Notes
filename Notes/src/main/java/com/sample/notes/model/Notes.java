package com.sample.notes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notes")
public class Notes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String note;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName = "user_id")
	private NoteUsers note_users;
	
	public Notes() {
		
	}

	public Notes(Long id, String note, Long user_id) {
		super();
		this.id = id;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public NoteUsers getNote_users() {
		return note_users;
	}

	public void setNote_users(NoteUsers note_users) {
		this.note_users = note_users;
	}
	
}
