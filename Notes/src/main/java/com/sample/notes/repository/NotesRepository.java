package com.sample.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.notes.model.Notes;

@Repository
public interface NotesRepository  extends JpaRepository<Notes, Long> {
	
	
}
