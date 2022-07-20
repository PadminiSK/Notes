package com.sample.notes.controller;

import java.rmi.ServerException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.notes.model.NoteUsers;
import com.sample.notes.model.Notes;
import com.sample.notes.repository.NoteUsersRepository;
import com.sample.notes.repository.NotesRepository;

@RestController
public class NotesController {

	@Autowired
	NoteUsersRepository noteUsersRepo;

	@Autowired
	NotesRepository notesRepo;

	// registered the user
	@PostMapping("/create/note-user")
	public ResponseEntity<NoteUsers> create(@RequestBody NoteUsers newUser) throws ServerException {
		newUser.setCreated_at(new Date());
		newUser.setUpdated_at(new Date());
		NoteUsers user = noteUsersRepo.save(newUser);
		if (user == null) {
			throw new ServerException("Error in creating new user");
		} else {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
	}

	// create notes of user
	@PostMapping("/note-user/{email}/new-note")
	public ResponseEntity<Notes> createNoteForAUser(@PathVariable String email, @RequestBody Notes note)
			throws ServerException {
		Notes newNote = null;

		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				note.setNote_users(noteUser);
				newNote = notesRepo.save(note);
				break;
			}
		}
		if (newNote == null) {
			throw new ServerException("the user is not registered");
		} else {
			return new ResponseEntity<>(newNote, HttpStatus.CREATED);
		}
	}

	// get the notes of user
	@GetMapping("/note-user/fetch-notes/{email}")
	public List<Optional<Notes>> getNotesOfUser(@PathVariable String email) throws ServerException {
		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		List<Notes> notesList = null;
		List<Optional<Notes>> notesListOfUser = new ArrayList<>();
		Optional<Notes> note1;
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				notesList = notesRepo.findAll();
				for (Notes note : notesList) {
					if (noteUser.getUser_Id() == note.getNote_users().getUser_Id()) {
						note1 = notesRepo.findById(note.getId());
						notesListOfUser.add(note1);
					}
				}
				break;
			}
		}
		if (notesListOfUser != null) {
			return notesListOfUser;
		} else {
			throw new ServerException("the user is not registered");
		}
	}

	@PutMapping("/note-user/{email}/update-notes")
	public ResponseEntity<Notes> updateNoteForSpecificUser(@PathVariable String email,
			@RequestBody Notes note) throws ServerException {
		Notes newNote = null;

		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				Notes specificNote = notesRepo.getById(note.getId());
				if(specificNote != null) {
					specificNote.setNote_users(noteUser);
					specificNote.setNote(note.getNote());
				newNote = notesRepo.save(specificNote);
				break;
				}
			}
		}
		if (newNote == null) {
			throw new ServerException("the user is not registered");
		} else {
			return new ResponseEntity<>(newNote, HttpStatus.OK);
		}
	}

}
